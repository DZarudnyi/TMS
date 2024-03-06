package layron.tms.mapper;

import layron.tms.config.MapperConfig;
import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.model.Project;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(CreateProjectRequestDto requestDto);

    ProjectDto toDto(Project project);
}
