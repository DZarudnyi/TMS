package layron.tms.controller;

import java.util.List;
import layron.tms.dto.comment.CommentDto;
import layron.tms.dto.comment.PostCommentRequestDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto postComment(
            @RequestBody PostCommentRequestDto requestDto
    ) throws UserNotFoundException {
        return commentService.saveComment(requestDto);
    }

    @GetMapping
    public List<CommentDto> getCommentsForTask(@RequestParam Long taskId) {
        return commentService.getCommentsForTask(taskId);
    }
}
