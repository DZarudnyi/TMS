package layron.tms.service.attachment;

import com.dropbox.core.DbxException;
import java.io.IOException;
import java.util.List;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.exception.FileTooBigException;
import layron.tms.exception.TaskNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentDto upload(Long taskId, MultipartFile file)
            throws IOException, DbxException, TaskNotFoundException, FileTooBigException;

    List<AttachmentDto> getAttachmentsForTask(Long taskId)
            throws DbxException;
}
