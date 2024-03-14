package layron.tms.service.attachment;

import com.dropbox.core.DbxException;
import java.io.IOException;
import java.util.List;
import layron.tms.dto.attachment.AttachmentDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentDto upload(Long taskId, MultipartFile file) throws IOException, DbxException;

    List<ResponseEntity<InputStreamResource>> getAttachmentsForTask(Long taskId)
            throws DbxException;
}
