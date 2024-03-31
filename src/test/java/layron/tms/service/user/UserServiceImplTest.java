package layron.tms.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;
import layron.tms.dto.user.UpdateUserRequestDto;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.mapper.UserMapper;
import layron.tms.model.Role;
import layron.tms.model.RoleName;
import layron.tms.model.User;
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

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_EMAIL = "email@example.com";
    private static final String DEFAULT_USERNAME = "username";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_NAME = "John";
    private static final String DEFAULT_SURNAME = "Doe";
    private User user;
    private UserDto userDto;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(DEFAULT_ID);
        user.setEmail(DEFAULT_EMAIL);
        user.setUsername(DEFAULT_USERNAME);
        user.setPassword(DEFAULT_PASSWORD);
        user.setFirstName(DEFAULT_NAME);
        user.setLastName(DEFAULT_SURNAME);
        user.setRoles(Set.of(new Role(DEFAULT_ID, RoleName.ROLE_USER)));

        userDto = new UserDto(
                DEFAULT_ID,
                DEFAULT_EMAIL,
                DEFAULT_NAME,
                DEFAULT_SURNAME
        );
    }

    @Test
    void getUser_Ok() throws UserNotFoundException {
        setupUserAuthorization();

        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername(DEFAULT_USERNAME);
        Mockito.doReturn(userDto).when(userMapper).toDto(user);

        UserDto actual = userService.getUser();
        assertNotNull(actual);
        assertEquals(userDto, actual);
    }

    @Test
    void updateUser_WithValidRequest_Ok() throws UserNotFoundException {
        setupUserAuthorization();

        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername(DEFAULT_USERNAME);
        Mockito.doReturn(userDto).when(userMapper).toDto(user);

        UpdateUserRequestDto requestDto = getUpdateUserRequestDto();
        UserDto actual = userService.updateUser(requestDto);

        assertNotNull(actual);
        assertEquals(userDto, actual);
    }

    @Test
    void updateUserRoles_WithValidRequest_Ok() throws UserNotFoundException {
        UpdateUserRoleRequestDto requestDto = getUpdateUserRoleRequestDto();
        UpdateUserRoleResponseDto expected = getUpdateUserRoleResponseDto();

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(DEFAULT_ID);
        Mockito.doReturn(user).when(userMapper).toEntity(requestDto);
        Mockito.doReturn(expected).when(userMapper).toDtoWithRoles(user);

        UpdateUserRoleResponseDto actual = userService.updateUserRoles(DEFAULT_ID, requestDto);
        assertNotNull(actual);
        assertEquals(expected, actual);
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

    private UpdateUserRequestDto getUpdateUserRequestDto() {
        return new UpdateUserRequestDto(
                DEFAULT_EMAIL,
                DEFAULT_NAME,
                DEFAULT_SURNAME
        );
    }

    private UpdateUserRoleResponseDto getUpdateUserRoleResponseDto() {
        return new UpdateUserRoleResponseDto(
                DEFAULT_ID,
                DEFAULT_EMAIL,
                DEFAULT_NAME,
                DEFAULT_SURNAME,
                Set.of(1L)
        );
    }

    private UpdateUserRoleRequestDto getUpdateUserRoleRequestDto() {
        return new UpdateUserRoleRequestDto(Set.of(1L));
    }
}
