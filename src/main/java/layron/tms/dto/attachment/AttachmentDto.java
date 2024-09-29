package layron.tms.dto.attachment;

import java.time.LocalDateTime;

public record AttachmentDto(
        Long id,
        Long taskId,
        String dropboxFileId,
        String filename,
        LocalDateTime uploadDate
) {
    public AttachmentDto() {
        this(0L, 0L, "Empty", "Empty", LocalDateTime.now());
    }
}
