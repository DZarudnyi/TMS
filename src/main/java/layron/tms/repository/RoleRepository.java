package layron.tms.repository;

import java.util.Optional;
import layron.tms.model.Role;
import layron.tms.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
