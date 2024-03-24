package layron.tms.controller;

import java.util.List;
import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.dto.project.UpdateProjectRequestDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    //TODO: Check format for endDate in incoming dto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(
            @RequestBody CreateProjectRequestDto requestDto
    ) throws UserNotFoundException {
        return projectService.save(requestDto);
    }

    @GetMapping
    public List<ProjectDto> getUserProjects() throws UserNotFoundException {
        return projectService.getUserProjects();
    }

    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProjectDto updateProject(
            @PathVariable Long id,
            @RequestBody UpdateProjectRequestDto requestDto
    ) {
        return projectService.updateProject(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }
}
