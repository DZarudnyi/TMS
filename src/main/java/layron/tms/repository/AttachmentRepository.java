package layron.tms.repository;

import java.util.List;
import java.util.Optional;
import layron.tms.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("SELECT a "
            + "FROM Attachment a "
            + "JOIN FETCH a.task at "
            + "WHERE at.id = (:taskId)")
    List<Attachment> getAttachmentByTaskId(Long taskId);

    @Query("SELECT a "
            + "FROM Attachment  a "
            + "WHERE a.dropboxFileId = (:id)")
    Optional<Attachment> getAttachmentByDropboxFileId(String id);
}
