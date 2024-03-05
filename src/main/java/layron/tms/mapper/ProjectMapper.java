package layron.tms.mapper;

import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.model.Project;

public interface ProjectMapper {
    Project toEntity(CreateProjectRequestDto requestDto);

    ProjectDto toDto(Project project);
}
