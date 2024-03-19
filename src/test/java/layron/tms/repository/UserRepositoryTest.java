package layron.tms.repository;

import layron.tms.exception.UserNotFoundException;
import layron.tms.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() throws UserNotFoundException {
        User actual = userRepository.findByEmail("email@example.com")
                .orElseThrow(() -> new UserNotFoundException("No user with such email"));

        assertEquals("John", actual.getFirstName());
        assertEquals("username", actual.getUsername());
    }

    @Test
    void findByUsername() throws UserNotFoundException {
        User actual = userRepository.findByUsername("username")
                .orElseThrow(() -> new UserNotFoundException("No user with such email"));

        assertEquals("Doe", actual.getLastName());
        assertEquals("email@example.com", actual.getEmail());
    }
}