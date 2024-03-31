package layron.tms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import layron.tms.model.Priority;
import layron.tms.model.Status;
import layron.tms.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {
        "classpath:database/delete-all-from-tasks.sql",
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql",
        "classpath:database/insert-testing-project.sql",
        "classpath:database/insert-testing-task.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/delete-all-from-tasks.sql",
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByProjectId_WithValidId_Ok() {
        List<Task> actual = taskRepository.findByProjectId(1L);

        assertEquals(1, actual.size());
        assertEquals("task", actual.get(0).getName());
        assertEquals(Priority.LOW, actual.get(0).getPriority());
    }

    @Test
    void findByProjectIdAndId_WithValidIds_Ok() {
        Task actual = taskRepository.findTaskById(1L).get();

        assertEquals(1L, actual.getProject().getId());
        assertEquals(Status.IN_PROGRESS, actual.getStatus());
    }
}
