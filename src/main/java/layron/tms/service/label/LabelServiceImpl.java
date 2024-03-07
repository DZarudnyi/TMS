package layron.tms.service.label;

import java.util.List;
import layron.tms.dto.label.CreateLabelRequestDto;
import layron.tms.dto.label.LabelDto;
import layron.tms.dto.label.UpdateLabelRequestDto;
import layron.tms.mapper.LabelMapper;
import layron.tms.model.Label;
import layron.tms.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelDto createLabel(CreateLabelRequestDto requestDto) {
        return labelMapper.toDto(labelRepository.save(labelMapper.toEntity(requestDto)));
    }

    @Override
    public List<LabelDto> getAllLabels() {
        return labelRepository.findAll().stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelDto updateLabel(Long id, UpdateLabelRequestDto requestDto) {
        Label label = labelMapper.toEntity(requestDto);
        label.setId(id);
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public void deleteLabel(Long id) {
        labelRepository.deleteById(id);
    }
}
