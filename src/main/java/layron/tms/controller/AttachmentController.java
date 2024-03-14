package layron.tms.controller;

import com.dropbox.core.DbxException;
import java.io.IOException;
import java.util.List;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.service.attachment.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping
    public AttachmentDto uploadAttachment(
            @RequestParam Long taskId,
            @RequestParam("file") MultipartFile file
    ) throws IOException, DbxException {
        return attachmentService.upload(taskId, file);
    }

    @GetMapping
    public List<ResponseEntity<InputStreamResource>> getAttachmentsForTask(
            @RequestParam Long taskId
    ) throws DbxException {
        return attachmentService.getAttachmentsForTask(taskId);
    }
}
