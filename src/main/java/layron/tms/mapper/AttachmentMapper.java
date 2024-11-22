package layron.tms.mapper;

import layron.tms.config.MapperConfig;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface AttachmentMapper {
    @Mapping(source = "task.id", target = "taskId")
    AttachmentDto toDto(Attachment attachment);
}
