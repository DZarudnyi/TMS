package layron.tms.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
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

@Sql(scripts = {
        "classpath:database/delete-all-from-tasks-labels.sql",
        "classpath:database/delete-all-from-tasks.sql",
        "classpath:database/delete-all-from-projects.sql",
        "classpath:database/delete-all-from-users-roles.sql",
        "classpath:database/delete-all-from-roles.sql",
        "classpath:database/delete-all-from-users.sql",
        "classpath:database/insert-testing-user.sql",
        "classpath:database/insert-testing-roles.sql",
        "classpath:database/insert-testing-users-roles.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/delete-all-from-users-roles.sql",
        "classpath:database/delete-all-from-roles.sql",
        "classpath:database/delete-all-from-users.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersControllerTest {
    protected static MockMvc mockMvc;
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_EMAIL = "email@example.com";
    private static final String DEFAULT_FIRST_NAME = "John";
    private static final String DEFAULT_LAST_NAME = "Doe";
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
    @WithMockUser(username = "username", roles = {"ADMIN"})
    void updateRole_WithValidRequest_Ok() throws Exception {
        UpdateUserRoleResponseDto expected = new UpdateUserRoleResponseDto(
                DEFAULT_ID,
                DEFAULT_EMAIL,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                Set.of(DEFAULT_ID)
        );
        UpdateUserRoleRequestDto requestDto = new UpdateUserRoleRequestDto(Set.of(2L));
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/users/1/role")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        UpdateUserRoleResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UpdateUserRoleResponseDto.class
        );
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void updateRole_WithUserRole_ReturnsStatus403() throws Exception {
        UpdateUserRoleRequestDto requestDto = new UpdateUserRoleRequestDto(Set.of(2L));
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/users/1/role")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "username")
    void getMyProfile_Ok() throws Exception {
        UserDto expected = getUserDto();

        MvcResult result = mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andReturn();

        UserDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "username")
    void updateMyProfile_WithValidRequest_Ok() throws Exception {
        UpdateUserRequestDto requestDto = new UpdateUserRequestDto(
                DEFAULT_EMAIL,
                "John",
                "Doesn't"
        );
        UserDto expected = new UserDto(
                DEFAULT_ID,
                DEFAULT_EMAIL,
                "John",
                "Doesn't"
        );
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(patch("/users/me")
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
