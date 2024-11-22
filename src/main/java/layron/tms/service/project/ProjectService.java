package layron.tms.service.project;

import java.util.List;
import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.dto.project.UpdateProjectRequestDto;
import layron.tms.exception.UserNotFoundException;

public interface ProjectService {
    ProjectDto save(CreateProjectRequestDto requestDto) throws UserNotFoundException;

    List<ProjectDto> getUserProjects() throws UserNotFoundException;

    ProjectDto getProjectById(Long id);

    ProjectDto updateProject(Long id, UpdateProjectRequestDto requestDto);

    void deleteProjectById(Long id);
}
