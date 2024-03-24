package layron.tms.repository;

import java.util.List;
import layron.tms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t "
            + "FROM Task t "
            + "JOIN FETCH t.project tp "
            + "WHERE tp.id = (:projectId)")
    List<Task> findByProjectId(Long projectId);

    @Query("SELECT t "
            + "FROM Task t "
            + "JOIN FETCH t.project tp "
            + "WHERE tp.id = (:projectId) "
            + "AND t.id = (:id)")
    Task findByProjectIdAndId(Long projectId, Long id);
}
