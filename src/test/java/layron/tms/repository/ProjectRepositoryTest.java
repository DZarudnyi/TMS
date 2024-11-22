package layron.tms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import layron.tms.model.Project;
import layron.tms.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {
        "classpath:database/delete-all-from-tasks-labels.sql",
        "classpath:database/delete-all-from-tasks.sql",
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users-roles.sql",
        "classpath:database/delete-all-from-roles.sql",
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql",
        "classpath:database/insert-testing-project.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getProjectsByUserUsername_WithValidUsername_Ok() {
        List<Project> actual = projectRepository.getProjectsByUserUsername("username");

        assertEquals(1, actual.size());
        assertEquals("description", actual.get(0).getDescription());
        assertEquals(Status.IN_PROGRESS, actual.get(0).getStatus());
        assertEquals(1L, actual.get(0).getUser().getId());
    }
}
