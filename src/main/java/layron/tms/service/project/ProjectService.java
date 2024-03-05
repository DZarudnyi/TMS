package layron.tms.service.project;

import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.dto.project.UpdateProjectRequestDto;

import java.util.List;

public interface ProjectService {
    ProjectDto save(CreateProjectRequestDto requestDto);

    List<ProjectDto> getUserProjects();

    ProjectDto getProjectById(Long id);

    ProjectDto updateProject(Long id, UpdateProjectRequestDto requestDto);

    void deleteProjectById(Long id);
}
