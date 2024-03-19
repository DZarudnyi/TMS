package layron.tms.repository;

import layron.tms.model.Project;
import layron.tms.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql",
        "classpath:database/insert-testing-project.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getProjectsByUserUsername() {
        List<Project> actual = projectRepository.getProjectsByUserUsername("username");

        assertEquals(1, actual.size());
        assertEquals("description", actual.get(0).getDescription());
        assertEquals(Status.IN_PROGRESS, actual.get(0).getStatus());
        assertEquals(1L, actual.get(0).getUser().getId());
    }
}