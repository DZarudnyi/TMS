package layron.tms.mapper;

import layron.tms.config.MapperConfig;
import layron.tms.dto.comment.CommentDto;
import layron.tms.dto.comment.PostCommentRequestDto;
import layron.tms.model.Comment;
import layron.tms.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "taskId", target = "task")
    Comment toEntity(PostCommentRequestDto requestDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "task.id", target = "taskId")
    CommentDto toDto(Comment comment);

    default Task mapIdToTask(Long taskId) {
        if (taskId == null) {
            return new Task();
        }
        Task task = new Task();
        task.setId(taskId);
        return task;
    }
}
