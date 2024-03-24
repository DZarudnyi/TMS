package layron.tms.service.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import layron.tms.dto.comment.CommentDto;
import layron.tms.dto.comment.PostCommentRequestDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.mapper.CommentMapper;
import layron.tms.model.Comment;
import layron.tms.model.Role;
import layron.tms.model.RoleName;
import layron.tms.model.Task;
import layron.tms.model.User;
import layron.tms.repository.CommentRepository;
import layron.tms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_TEXT = "This is a comment";
    private Comment comment;
    private CommentDto commentDto;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setup() {
        comment = new Comment();
        comment.setId(DEFAULT_ID);
        comment.setTask(getTask());
        comment.setText(DEFAULT_TEXT);
        LocalDateTime timestamp = LocalDateTime.now();
        comment.setTimestamp(timestamp);
        comment.setUser(getUser());

        commentDto = new CommentDto(
                DEFAULT_ID,
                DEFAULT_ID,
                DEFAULT_ID,
                timestamp
        );
    }

    @Test
    void saveComment_WithValidRequest_Ok() throws UserNotFoundException {
        PostCommentRequestDto requestDto = getPostCommentRequestDto();

        setupUserAuthorization();
        Mockito.doReturn(comment).when(commentMapper).toEntity(requestDto);
        Mockito.doReturn(commentDto).when(commentMapper).toDto(comment);
        Mockito.doReturn(comment).when(commentRepository).save(comment);

        CommentDto actual = commentService.saveComment(requestDto);
        assertNotNull(actual);
        assertEquals(commentDto, actual);
    }

    @Test
    void getCommentsForTask_Ok() {
        Mockito.when(commentRepository.getCommentByTaskId(DEFAULT_ID)).thenReturn(List.of(comment));
        Mockito.when(commentMapper.toDto(comment)).thenReturn(commentDto);

        List<CommentDto> actual = commentService.getCommentsForTask(DEFAULT_ID);
        assertNotNull(actual);
        assertEquals(List.of(commentDto), actual);
    }

    private void setupUserAuthorization() {
        User user = getUser();
        authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private User getUser() {
        User user = new User();
        user.setId(DEFAULT_ID);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setFirstName("name");
        user.setLastName("surname");
        user.setRoles(Set.of(new Role(DEFAULT_ID, RoleName.ROLE_USER)));
        return user;
    }

    private PostCommentRequestDto getPostCommentRequestDto() {
        return new PostCommentRequestDto(DEFAULT_ID, DEFAULT_TEXT);
    }

    private Task getTask() {
        Task task = new Task();
        task.setId(DEFAULT_ID);
        return task;
    }
}
