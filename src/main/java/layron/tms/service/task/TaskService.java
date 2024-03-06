package layron.tms.service.task;

import layron.tms.dto.task.CreateTaskRequestDto;
import layron.tms.dto.task.TaskDto;
import layron.tms.dto.task.UpdateTaskRequestDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(Long projectId, CreateTaskRequestDto requestDto);

    List<TaskDto> getTasksForProject(Long projectId);

    TaskDto getTask(Long projectId, Long id);

    TaskDto updateTask(Long projectId, Long id, UpdateTaskRequestDto requestDto);

    void deleteTask(Long id);
}