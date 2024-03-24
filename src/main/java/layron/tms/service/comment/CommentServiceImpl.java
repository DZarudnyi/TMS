package layron.tms.service.comment;

import java.time.LocalDateTime;
import java.util.List;
import layron.tms.dto.comment.CommentDto;
import layron.tms.dto.comment.PostCommentRequestDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.mapper.CommentMapper;
import layron.tms.model.Comment;
import layron.tms.model.User;
import layron.tms.repository.CommentRepository;
import layron.tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto saveComment(PostCommentRequestDto requestDto) throws UserNotFoundException {
        Comment comment = commentMapper.toEntity(requestDto);
        comment.setUser(getUser());
        comment.setTimestamp(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsForTask(Long taskId) {
        return commentRepository.getCommentByTaskId(taskId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    private User getUser() throws UserNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException("User with this username does not exist!")
                );
    }
}
