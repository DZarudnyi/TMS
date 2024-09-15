package layron.tms.controller;

import java.util.List;
import layron.tms.dto.task.CreateTaskRequestDto;
import layron.tms.dto.task.TaskDto;
import layron.tms.dto.task.UpdateTaskRequestDto;
import layron.tms.exception.TaskNotFoundException;
import layron.tms.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(
            @PathVariable Long projectId,
            @RequestBody CreateTaskRequestDto requestDto
    ) {
        return taskService.createTask(projectId, requestDto);
    }

    @GetMapping
    public List<TaskDto> getTasksForProject(@PathVariable Long projectId) {
        return taskService.getTasksForProject(projectId);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) throws TaskNotFoundException {
        return taskService.getTask(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateTask(
            @PathVariable Long projectId,
            @PathVariable Long id,
            @RequestBody UpdateTaskRequestDto requestDto
    ) {
        return taskService.updateTask(projectId, id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
