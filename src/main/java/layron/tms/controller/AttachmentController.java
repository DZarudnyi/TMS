package layron.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {
    @PostMapping
    public AttachmentDto uploadAttachment() {
        //
    }

    @GetMapping
    public AttachmentDto getAttachmentsForTask(@RequestParam Long taskId) {
        //
    }
}
