package layron.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import layron.tms.dto.task.CreateTaskRequestDto;
import layron.tms.dto.task.TaskDto;
import layron.tms.dto.task.UpdateTaskRequestDto;
import layron.tms.model.Priority;
import layron.tms.model.Status;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(scripts = {
        "classpath:database/delete-all-from-comments.sql",
        "classpath:database/delete-all-from-tasks.sql",
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql",
        "classpath:database/insert-testing-project.sql",
        "classpath:database/insert-testing-task.sql",
        "classpath:database/insert-testing-comment.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {
    protected static MockMvc mockMvc;
    private static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_NAME = "task";
    public static final int DEFAULT_STATUS = 1;
    public static final int DEFAULT_PRIORITY = 1;
    public static final LocalDate DEFAULT_DUE_DATE = LocalDate.now().plusDays(1);
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void createTask() throws Exception {
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto(
                DEFAULT_NAME,
                DEFAULT_PRIORITY,
                DEFAULT_DUE_DATE,
                DEFAULT_ID
        );
        TaskDto expected = getTaskDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/api/1/tasks")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        TaskDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), TaskDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void getTasksForProject() throws Exception {
        List<TaskDto> expected = List.of(getTaskDto());

        MvcResult result = mockMvc.perform(get("/api/1/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        TaskDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), TaskDto[].class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void getTask() throws Exception {
        TaskDto expected = getTaskDto();

        MvcResult result = mockMvc.perform(get("/api/1/tasks/1"))
                .andExpect(status().isOk())
                .andReturn();

        TaskDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), TaskDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void updateTask() throws Exception {
        UpdateTaskRequestDto requestDto = new UpdateTaskRequestDto(
                DEFAULT_NAME,
                DEFAULT_PRIORITY,
                DEFAULT_STATUS,
                DEFAULT_DUE_DATE,
                DEFAULT_ID
        );
        TaskDto expected = getTaskDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/api/1/tasks/1")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        TaskDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), TaskDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/1/tasks/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private TaskDto getTaskDto() {
        return new TaskDto(
                DEFAULT_ID,
                DEFAULT_NAME,
                Priority.valueOf(DEFAULT_PRIORITY),
                Status.valueOf(DEFAULT_STATUS),
                DEFAULT_DUE_DATE,
                DEFAULT_ID,
                DEFAULT_ID
        );
    }
}