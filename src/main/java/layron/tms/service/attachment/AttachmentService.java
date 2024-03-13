package layron.tms.service.attachment;

import com.dropbox.core.DbxException;
import layron.tms.dto.attachment.AttachmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    AttachmentDto upload(Long taskId, MultipartFile file) throws IOException, DbxException;

    List<MultipartFile> getAttachmentsForTask(Long taskId) throws DbxException;
}
