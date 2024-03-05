package layron.tms.repository;

import layron.tms.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = (:email)")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = (:username)")
    Optional<User> findByUsername(String username);
}