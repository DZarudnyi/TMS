package layron.tms.service.label;

import layron.tms.dto.label.CreateLabelRequestDto;
import layron.tms.dto.label.LabelDto;
import layron.tms.dto.label.UpdateLabelRequestDto;
import layron.tms.mapper.LabelMapper;
import layron.tms.model.Label;
import layron.tms.repository.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LabelServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_COLOR = "Blue";
    private static final String DEFAULT_NAME = "Label";
    private Label label;
    private LabelDto labelDto;

    @Mock
    private LabelRepository labelRepository;
    @Mock
    private LabelMapper labelMapper;
    @InjectMocks
    private LabelServiceImpl labelService;

    @BeforeEach
    public void setup() {
        label = new Label();
        label.setId(DEFAULT_ID);
        label.setColor(DEFAULT_COLOR);
        label.setName(DEFAULT_NAME);

        labelDto = new LabelDto(
                DEFAULT_ID,
                DEFAULT_NAME,
                DEFAULT_COLOR
        );
    }

    @Test
    void createLabel() {
        CreateLabelRequestDto requestDto = getCreateLabelRequestDto();
        Mockito.doReturn(label).when(labelMapper).toEntity(requestDto);
        Mockito.doReturn(label).when(labelRepository).save(label);
        Mockito.doReturn(labelDto).when(labelMapper).toDto(label);

        LabelDto actual = labelService.createLabel(requestDto);
        assertNotNull(actual);
        assertEquals(labelDto, actual);
    }

    @Test
    void getAllLabels() {
        List<LabelDto> expected = List.of(labelDto);

        Mockito.doReturn(labelDto).when(labelMapper).toDto(label);
        Mockito.doReturn(List.of(label)).when(labelRepository).findAll();

        List<LabelDto> actual = labelService.getAllLabels();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateLabel() {
        UpdateLabelRequestDto requestDto = getUpdateLabelRequestDto();

        Mockito.doReturn(label).when(labelMapper).toEntity(requestDto);
        Mockito.doReturn(label).when(labelRepository).save(label);
        Mockito.doReturn(labelDto).when(labelMapper).toDto(label);

        LabelDto actual = labelService.updateLabel(DEFAULT_ID, requestDto);
        assertNotNull(actual);
        assertEquals(labelDto, actual);
    }

    @Test
    void deleteLabel() {
        labelService.deleteLabel(DEFAULT_ID);
        Mockito.verify(labelRepository, Mockito.times(1))
                .deleteById(DEFAULT_ID);
    }

    private CreateLabelRequestDto getCreateLabelRequestDto() {
        return new CreateLabelRequestDto(
                DEFAULT_NAME,
                DEFAULT_COLOR
        );
    }

    private UpdateLabelRequestDto getUpdateLabelRequestDto() {
        return new UpdateLabelRequestDto(
                DEFAULT_NAME,
                DEFAULT_COLOR
        );
    }
}