package layron.tms.service.user;

import java.util.Set;
import layron.tms.config.SecurityConfig;
import layron.tms.dto.user.UpdateUserRequestDto;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
import layron.tms.dto.user.UserRegistrationRequestDto;
import layron.tms.dto.user.UserRegistrationResponseDto;
import layron.tms.exception.RegistrationException;
import layron.tms.exception.UserNotFoundException;
import layron.tms.mapper.UserMapper;
import layron.tms.model.Role;
import layron.tms.model.RoleName;
import layron.tms.model.User;
import layron.tms.repository.RoleRepository;
import layron.tms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final SecurityConfig config;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User already exists!");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(config.getPasswordEncoder().encode(requestDto.getPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RegistrationException("There is no role for this user!"));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    //TODO: Is there any logic to use this exception here?
    @Override
    public UserDto getUser() throws UserNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("There is no user with such username!"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(
            UpdateUserRequestDto requestDto
    ) throws UserNotFoundException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(loggedInUser.getName())
                .orElseThrow(() -> new UserNotFoundException("There is no user with such username!"));
        if (requestDto.email() != null && !requestDto.email().isEmpty()) {
            user.setEmail(requestDto.email());
        }
        if (requestDto.firstName() != null && !requestDto.firstName().isEmpty()) {
            user.setFirstName(requestDto.firstName());
        }
        if (requestDto.lastName() != null && !requestDto.lastName().isEmpty()) {
            user.setLastName(requestDto.lastName());
        }
        return userMapper.toDto(user);
    }

    @Override
    public UpdateUserRoleResponseDto updateUserRoles(
            Long id,
            UpdateUserRoleRequestDto requestDto
    ) throws UserNotFoundException {
        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id " + id));
        User user = userMapper.toEntity(requestDto);
        userFromDb.setRoles(user.getRoles());
        return userMapper.toDtoWithRoles(userFromDb);
    }
}
