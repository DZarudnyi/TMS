package layron.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import layron.tms.dto.user.UpdateUserRequestDto;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
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
import java.util.Set;

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
class UsersControllerTest {
    protected static MockMvc mockMvc;
    private static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_EMAIL = "email@example.com";
    public static final String DEFAULT_FIRST_NAME = "John";
    public static final String DEFAULT_LAST_NAME = "Doe";
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
    void updateRole() throws Exception {
        UpdateUserRoleResponseDto expected = new UpdateUserRoleResponseDto(
                DEFAULT_ID,
                DEFAULT_EMAIL,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                Set.of(DEFAULT_ID)
        );
        UpdateUserRoleRequestDto requestDto = new UpdateUserRoleRequestDto(Set.of(2L));
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/api/users/1/role").
                content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        UpdateUserRoleResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), UpdateUserRoleResponseDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void getMyProfile() throws Exception {
        UserDto expected = getUserDto();

        MvcResult result = mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andReturn();

        UserDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void updateMyProfile() throws Exception {
        //TODO: update user properly
        UpdateUserRequestDto requestDto = new UpdateUserRequestDto();
        UserDto expected = getUserDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(patch("/api/users/me")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        UserDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    private UserDto getUserDto() {
        return new UserDto(
                DEFAULT_ID,
                DEFAULT_EMAIL,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME
        );
    }
}