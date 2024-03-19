package layron.tms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import layron.tms.dto.comment.CommentDto;
import layron.tms.dto.comment.PostCommentRequestDto;
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

import java.io.UnsupportedEncodingException;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
class CommentControllerTest {
    protected static MockMvc mockMvc;
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
    @WithMockUser
    void postComment() throws JsonProcessingException, UnsupportedEncodingException {
        PostCommentRequestDto requestDto = new PostCommentRequestDto(
                1L,
                "some text"
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/api/comments")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CommentDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), CommentDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                expected,
                actual
        );
    }

    @Test
    void getCommentsForTask() {
    }
}