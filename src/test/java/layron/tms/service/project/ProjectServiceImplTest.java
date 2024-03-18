package layron.tms.service.project;

import layron.tms.dto.project.CreateProjectRequestDto;
import layron.tms.dto.project.ProjectDto;
import layron.tms.dto.project.UpdateProjectRequestDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.mapper.ProjectMapper;
import layron.tms.model.Project;
import layron.tms.model.Role;
import layron.tms.model.RoleName;
import layron.tms.model.Status;
import layron.tms.model.User;
import layron.tms.repository.ProjectRepository;
import layron.tms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "Project";
    private static final String DEFAULT_DESCRIPTION = "Description";
    private static final Status DEFAULT_STATUS = Status.INITIATED;
    private LocalDate endDate;
    private Project project;
    private ProjectDto projectDto;
    private User user;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void setup() {
        endDate = LocalDate.now().plusDays(1L);
        LocalDate startDate = LocalDate.now();

        project = new Project();
        project.setId(DEFAULT_ID);
        project.setStartDate(startDate);
        project.setName(DEFAULT_NAME);
        project.setDescription(DEFAULT_DESCRIPTION);
        project.setEndDate(endDate);
        project.setStatus(DEFAULT_STATUS);
        project.setUser(getUser());

        projectDto = new ProjectDto(
                DEFAULT_ID,
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                startDate,
                endDate
        );
    }

    @Test
    void save() throws UserNotFoundException {
        user = getUser();
        setupUserAuthorization();
        CreateProjectRequestDto requestDto = getCreateProjectRequestDto();

        Mockito.doReturn(Optional.of(user))
                .when(userRepository).findByUsername(user.getUsername());
        Mockito.doReturn(project).when(projectMapper).toEntity(requestDto);
        Mockito.doReturn(project).when(projectRepository).save(project);
        Mockito.doReturn(projectDto).when(projectMapper).toDto(project);

        ProjectDto actual = projectService.save(requestDto);
        assertNotNull(actual);
        assertEquals(projectDto, actual);
    }

    @Test
    void getUserProjects() {
        user = getUser();
        setupUserAuthorization();
        List<ProjectDto> expected = List.of(projectDto);

        Mockito.doReturn(List.of(project))
                .when(projectRepository).getProjectsByUserUsername(user.getUsername());
        Mockito.doReturn(projectDto).when(projectMapper).toDto(project);

        List<ProjectDto> actual = projectService.getUserProjects();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getProjectById() {
        Mockito.doReturn(project).when(projectRepository).getReferenceById(DEFAULT_ID);
        Mockito.doReturn(projectDto).when(projectMapper).toDto(project);

        ProjectDto actual = projectService.getProjectById(DEFAULT_ID);
        assertNotNull(actual);
        assertEquals(projectDto, actual);
    }

    @Test
    void updateProject() {
        UpdateProjectRequestDto requestDto = getUpdateProjectRequestDto();

        Mockito.doReturn(project).when(projectRepository).getReferenceById(DEFAULT_ID);
        Mockito.doReturn(projectDto).when(projectMapper).toDto(project);

        ProjectDto actual = projectService.updateProject(DEFAULT_ID, requestDto);
        assertNotNull(actual);
        assertEquals(projectDto, actual);
    }

    @Test
    void deleteProjectById() {
        projectService.deleteProjectById(DEFAULT_ID);
        Mockito.verify(projectRepository, Mockito.times(1))
                .deleteById(DEFAULT_ID);
    }

    private void setupUserAuthorization() {
        authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private CreateProjectRequestDto getCreateProjectRequestDto() {
        return new CreateProjectRequestDto(
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                endDate
        );
    }

    private User getUser() {
        User user = new User();
        user.setId(DEFAULT_ID);
        user.setEmail("email");
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("name");
        user.setLastName("surname");
        user.setRoles(Set.of(new Role(DEFAULT_ID, RoleName.ROLE_USER)));
        return user;
    }

    private UpdateProjectRequestDto getUpdateProjectRequestDto() {
        return new UpdateProjectRequestDto(
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                endDate
        );
    }
}