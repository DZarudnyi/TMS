package layron.tms.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import layron.tms.dto.label.CreateLabelRequestDto;
import layron.tms.dto.label.LabelDto;
import layron.tms.dto.label.UpdateLabelRequestDto;
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
        "classpath:database/delete-all-from-labels.sql",
        "classpath:database/insert-testing-label.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/delete-all-from-labels.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LabelControllerTest {
    protected static MockMvc mockMvc;
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "label";
    private static final String DEFAULT_COLOR = "red";
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
    void createLabel_WithValidRequest_Ok() throws Exception {
        CreateLabelRequestDto requestDto =
                new CreateLabelRequestDto(DEFAULT_NAME, DEFAULT_COLOR);

        LabelDto expected = getLabelDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/labels")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andReturn();

        LabelDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), LabelDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser
    void getAllLabels_Ok() throws Exception {
        List<LabelDto> expected = List.of(getLabelDto());

        MvcResult result = mockMvc.perform(get("/labels"))
                .andExpect(status().isOk())
                .andReturn();

        LabelDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), LabelDto[].class);

        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser
    void updateLabel_WithValidRequest_Ok() throws Exception {
        UpdateLabelRequestDto requestDto =
                new UpdateLabelRequestDto(DEFAULT_NAME, DEFAULT_COLOR);

        LabelDto expected = getLabelDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/labels/1")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted())
                    .andReturn();

        LabelDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), LabelDto.class);

        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser
    void deleteLabel_Ok() throws Exception {
        mockMvc.perform(delete("/labels/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private LabelDto getLabelDto() {
        return new LabelDto(
                DEFAULT_ID,
                DEFAULT_NAME,
                DEFAULT_COLOR
        );
    }
}
