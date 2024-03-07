package layron.tms.dto.comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long taskId,
        Long userId,
        LocalDateTime timestamp
) {
}
