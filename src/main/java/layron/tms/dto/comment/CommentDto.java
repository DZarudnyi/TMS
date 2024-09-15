package layron.tms.dto.comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long taskId,
        Long userId,
        String text,
        LocalDateTime timestamp
) {
}
