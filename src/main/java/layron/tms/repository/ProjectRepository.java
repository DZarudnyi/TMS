package layron.tms.repository;

import java.util.List;
import layron.tms.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p "
            + "FROM Project p "
            + "JOIN FETCH p.user pu "
            + "WHERE pu.username = (:username)")
    List<Project> getProjectsByUserUsername(String username);
}
