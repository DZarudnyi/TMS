package layron.tms.service.user;

import layron.tms.dto.user.UpdateUserRequestDto;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
import layron.tms.dto.user.UserRegistrationResponseDto;
import layron.tms.dto.user.UserRegistrationRequestDto;
import layron.tms.exception.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserDto getUser();

    UserDto updateUser(UpdateUserRequestDto requestDto);

    UpdateUserRoleResponseDto updateUserRoles(Long id, UpdateUserRoleRequestDto requestDto);
}
