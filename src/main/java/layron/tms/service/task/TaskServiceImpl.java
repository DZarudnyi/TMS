package layron.tms.service.task;

import java.util.List;
import layron.tms.dto.task.CreateTaskRequestDto;
import layron.tms.dto.task.TaskDto;
import layron.tms.dto.task.UpdateTaskRequestDto;
import layron.tms.mapper.TaskMapper;
import layron.tms.model.Project;
import layron.tms.model.Status;
import layron.tms.model.Task;
import layron.tms.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto createTask(Long projectId, CreateTaskRequestDto requestDto) {
        Task task = taskMapper.toEntity(requestDto);
        task.setStatus(Status.INITIATED);
        Project project = new Project();
        project.setId(projectId);
        task.setProject(project);
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getTasksForProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskDto getTask(Long projectId, Long id) {
        return taskMapper.toDto(taskRepository.findByProjectIdAndId(projectId, id));
    }

    @Override
    public TaskDto updateTask(Long projectId, Long id, UpdateTaskRequestDto requestDto) {
        Task task = taskMapper.toEntity(requestDto);
        Project project = new Project();
        project.setId(projectId);
        task.setProject(project);
        task.setId(id);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
