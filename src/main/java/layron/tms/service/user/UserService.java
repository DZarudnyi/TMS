package layron.tms.service.user;

import layron.tms.dto.user.UpdateUserRequestDto;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
import layron.tms.dto.user.UserRegistrationRequestDto;
import layron.tms.dto.user.UserRegistrationResponseDto;
import layron.tms.exception.RegistrationException;
import layron.tms.exception.UserNotFoundException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    UserDto getUser() throws UserNotFoundException;

    UserDto updateUser(UpdateUserRequestDto requestDto) throws UserNotFoundException;

    UpdateUserRoleResponseDto updateUserRoles(Long id, UpdateUserRoleRequestDto requestDto)
            throws UserNotFoundException;
}
