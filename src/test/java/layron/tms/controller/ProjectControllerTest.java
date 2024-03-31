package layron.tms.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.dto.project.UpdateProjectRequestDto;
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

@Sql(scripts = {
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql",
        "classpath:database/insert-testing-project.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {
    protected static MockMvc mockMvc;
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "project";
    private static final String DEFAULT_DESCRIPTION = "description";
    private static final LocalDate DEFAULT_END_DATE = LocalDate.now().plusDays(1);
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
    void createProject_WithValidRequest_Ok() throws Exception {
        CreateProjectRequestDto requestDto = new CreateProjectRequestDto(
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                DEFAULT_END_DATE
        );

        ProjectDto expected = getProjectDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/api/projects")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andReturn();

        ProjectDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void getUserProjects_Ok() throws Exception {
        List<ProjectDto> expected = List.of(getProjectDto());

        MvcResult result = mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProjectDto[].class
        );
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void getProject_WithValidId_Ok() throws Exception {
        ProjectDto expected = getProjectDto();

        MvcResult result = mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void updateProject_WithValidRequest_Ok() throws Exception {
        UpdateProjectRequestDto requestDto = new UpdateProjectRequestDto(
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                DEFAULT_END_DATE
        );
        ProjectDto expected = getProjectDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/api/projects/1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        ProjectDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void deleteProject_Ok() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private ProjectDto getProjectDto() {
        return new ProjectDto(
                DEFAULT_ID,
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                LocalDate.now(),
                DEFAULT_END_DATE
        );
    }
}
