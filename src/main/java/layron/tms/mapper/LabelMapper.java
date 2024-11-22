package layron.tms.mapper;

import layron.tms.config.MapperConfig;
import layron.tms.dto.label.CreateLabelRequestDto;
import layron.tms.dto.label.LabelDto;
import layron.tms.dto.label.UpdateLabelRequestDto;
import layron.tms.model.Label;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface LabelMapper {
    LabelDto toDto(Label label);

    Label toEntity(CreateLabelRequestDto requestDto);

    Label toEntity(UpdateLabelRequestDto requestDto);
}
