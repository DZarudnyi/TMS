package layron.tms.service.label;

import java.util.List;
import layron.tms.dto.label.CreateLabelRequestDto;
import layron.tms.dto.label.LabelDto;
import layron.tms.dto.label.UpdateLabelRequestDto;

public interface LabelService {
    LabelDto createLabel(CreateLabelRequestDto requestDto);

    List<LabelDto> getAllLabels();

    LabelDto updateLabel(Long id, UpdateLabelRequestDto requestDto);

    void deleteLabel(Long id);
}
