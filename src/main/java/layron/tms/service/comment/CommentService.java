package layron.tms.service.comment;

import java.util.List;
import layron.tms.dto.comment.CommentDto;
import layron.tms.dto.comment.PostCommentRequestDto;
import layron.tms.exception.UserNotFoundException;

public interface CommentService {
    CommentDto saveComment(PostCommentRequestDto requestDto) throws UserNotFoundException;

    List<CommentDto> getCommentsForTask(Long taskId);
}
