package layron.tms.mapper;

import layron.tms.config.MapperConfig;
import layron.tms.dto.task.CreateTaskRequestDto;
import layron.tms.dto.task.TaskDto;
import layron.tms.dto.task.UpdateTaskRequestDto;
import layron.tms.model.Priority;
import layron.tms.model.Status;
import layron.tms.model.Task;
import layron.tms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "assigneeId", target = "assignee")
    Task toEntity(CreateTaskRequestDto requestDto);

    @Mapping(source = "assigneeId", target = "assignee")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "status", target = "status")
    Task toEntity(UpdateTaskRequestDto requestDto);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskDto toDto(Task task);

    default User mapIdToUser(Long assigneeId) {
        if (assigneeId == null) {
            return new User();
        }
        User assignee = new User();
        assignee.setId(assigneeId);
        return assignee;
    }

    default Priority mapValueToPriority(int value) {
        Priority priority = Priority.valueOf(value);
        return priority == null ? Priority.LOW : priority;
    }

    default Status mapValueToStatus(int value) {
        Status status = Status.valueOf(value);
        return status == null ? Status.INITIATED : status;
    }
}
