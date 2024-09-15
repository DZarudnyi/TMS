package layron.tms.repository;

import java.util.List;
import layron.tms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c "
            + "JOIN FETCH c.task ct "
            + "JOIN FETCH c.user cu "
            + "WHERE ct.id = (:taskId)")
    List<Comment> getCommentByTaskId(Long taskId);
}
