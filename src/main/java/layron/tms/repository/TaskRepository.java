package layron.tms.repository;

import java.util.List;
import java.util.Optional;
import layron.tms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t "
            + "FROM Task t "
            + "JOIN FETCH t.project tp "
            + "LEFT JOIN FETCH t.labels tl "
            + "WHERE tp.id = (:projectId)")
    List<Task> findByProjectId(Long projectId);

    @Query("SELECT t "
            + "FROM Task t "
            + "JOIN FETCH t.project tp "
            + "LEFT JOIN FETCH t.labels tl "
            + "WHERE t.id = (:id)")
    Optional<Task> findTaskById(Long id);
}
