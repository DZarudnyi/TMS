package layron.tms.service.project;

import java.time.LocalDate;
import java.util.List;
import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.dto.project.UpdateProjectRequestDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.mapper.ProjectMapper;
import layron.tms.model.Project;
import layron.tms.model.Status;
import layron.tms.model.User;
import layron.tms.repository.ProjectRepository;
import layron.tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto save(
            CreateProjectRequestDto requestDto
    ) throws UserNotFoundException {
        Project project = projectMapper.toEntity(requestDto);
        project.setStartDate(LocalDate.now());
        project.setStatus(Status.INITIATED);
        project.setUser(getUser());
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectDto> getUserProjects() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return projectRepository.getProjectsByUserUsername(username).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        return projectMapper.toDto(projectRepository.getReferenceById(id));
    }

    @Override
    public ProjectDto updateProject(Long id, UpdateProjectRequestDto requestDto) {
        Project projectFromDb = projectRepository.getReferenceById(id);
        projectFromDb.setName(requestDto.name());
        projectFromDb.setDescription(requestDto.description());
        projectFromDb.setEndDate(requestDto.endDate());
        return projectMapper.toDto(projectFromDb);
    }

    @Override
    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    private User getUser() throws UserNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException("User with this username does not exist!")
                );
    }
}
