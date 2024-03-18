package layron.tms.service.task;

import layron.tms.dto.task.CreateTaskRequestDto;
import layron.tms.dto.task.TaskDto;
import layron.tms.dto.task.UpdateTaskRequestDto;
import layron.tms.mapper.TaskMapper;
import layron.tms.model.Priority;
import layron.tms.model.Project;
import layron.tms.model.Role;
import layron.tms.model.RoleName;
import layron.tms.model.Status;
import layron.tms.model.Task;
import layron.tms.model.User;
import layron.tms.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "Project";
    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Status DEFAULT_STATUS = Status.INITIATED;
    private LocalDate dueDate;
    private Task task;
    private TaskDto taskDto;

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setup() {
        dueDate = LocalDate.now().plusDays(1L);

        task = new Task();
        task.setId(DEFAULT_ID);
        task.setStatus(DEFAULT_STATUS);
        task.setProject(getProject());
        task.setAssignee(getUser());
        task.setName(DEFAULT_NAME);
        task.setPriority(DEFAULT_PRIORITY);

        taskDto = new TaskDto(
                DEFAULT_ID,
                DEFAULT_NAME,
                DEFAULT_PRIORITY,
                DEFAULT_STATUS,
                dueDate,
                DEFAULT_ID,
                DEFAULT_ID
        );
    }

    @Test
    void createTask() {
        CreateTaskRequestDto requestDto = getCreateTaskRequestDto();

        Mockito.doReturn(task).when(taskMapper).toEntity(requestDto);
        Mockito.doReturn(taskDto).when(taskMapper).toDto(task);

        TaskDto actual = taskService.createTask(DEFAULT_ID, requestDto);
        assertNotNull(actual);
        assertEquals(taskDto, actual);
    }

    @Test
    void getTasksForProject() {
        Mockito.doReturn(List.of(task)).when(taskRepository).findByProjectId(DEFAULT_ID);
        Mockito.doReturn(taskDto).when(taskMapper).toDto(task);

        List<TaskDto> actual = taskService.getTasksForProject(DEFAULT_ID);
        assertNotNull(actual);
        assertEquals(List.of(taskDto), actual);
    }

    @Test
    void getTask() {
        Mockito.doReturn(task).when(taskRepository).findByProjectIdAndId(DEFAULT_ID, DEFAULT_ID);
        Mockito.doReturn(taskDto).when(taskMapper).toDto(task);

        TaskDto actual = taskService.getTask(DEFAULT_ID, DEFAULT_ID);
        assertNotNull(actual);
        assertEquals(taskDto, actual);
    }

    @Test
    void updateTask() {
        UpdateTaskRequestDto requestDto = getUpdateTaskRequestDto();

        Mockito.doReturn(task).when(taskMapper).toEntity(requestDto);
        Mockito.doReturn(task).when(taskRepository).save(task);
        Mockito.doReturn(taskDto).when(taskMapper).toDto(task);

        TaskDto actual = taskService.updateTask(DEFAULT_ID, DEFAULT_ID, requestDto);
        assertNotNull(actual);
        assertEquals(taskDto, actual);
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(DEFAULT_ID);
        Mockito.verify(taskRepository, Mockito.times(1))
                .deleteById(DEFAULT_ID);
    }

    private CreateTaskRequestDto getCreateTaskRequestDto() {
        return new CreateTaskRequestDto(
                DEFAULT_NAME,
                1,
                dueDate,
                DEFAULT_ID
        );
    }

    private UpdateTaskRequestDto getUpdateTaskRequestDto() {
        return new UpdateTaskRequestDto(
                DEFAULT_NAME,
                1,
                1,
                dueDate,
                DEFAULT_ID
        );
    }

    private User getUser() {
        User user = new User();
        user.setId(DEFAULT_ID);
        user.setEmail("email");
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("name");
        user.setLastName("surname");
        user.setRoles(Set.of(new Role(DEFAULT_ID, RoleName.ROLE_USER)));
        return user;
    }

    private Project getProject() {
        Project project = new Project();
        project.setId(DEFAULT_ID);
        project.setStartDate(LocalDate.now());
        project.setName(DEFAULT_NAME);
        project.setDescription("Description");
        project.setEndDate(dueDate);
        project.setStatus(DEFAULT_STATUS);
        project.setUser(getUser());
        return project;
    }
}