package layron.tms.controller;

import com.dropbox.core.DbxException;
import java.io.IOException;
import java.util.List;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.exception.FileTooBigException;
import layron.tms.exception.TaskNotFoundException;
import layron.tms.service.attachment.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttachmentDto uploadAttachment(
            @RequestParam Long taskId,
            @RequestParam("file") MultipartFile file
    ) throws IOException, DbxException, TaskNotFoundException, FileTooBigException {
        return attachmentService.upload(taskId, file);
    }

    @GetMapping
    public List<AttachmentDto> getAttachmentsForTask(
            @RequestParam Long taskId
    ) throws DbxException {
        return attachmentService.getAttachmentsForTask(taskId);
    }
}
