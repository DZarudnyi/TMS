package layron.tms.repository;

import layron.tms.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("SELECT a FROM Attachment a JOIN FETCH a.task at WHERE at.id = (:taskId)")
    List<Attachment> getAttachmentByTaskId(Long taskId);
}
