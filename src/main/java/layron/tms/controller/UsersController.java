package layron.tms.controller;

import layron.tms.dto.user.UpdateUserRequestDto;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
import layron.tms.exception.UserNotFoundException;
import layron.tms.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdateUserRoleResponseDto updateRole(
            @PathVariable Long id,
            @RequestBody UpdateUserRoleRequestDto requestDto
    ) throws UserNotFoundException {
        return userService.updateUserRoles(id, requestDto);
    }

    @GetMapping("/me")
    public UserDto getMyProfile() throws UserNotFoundException {
        return userService.getUser();
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateMyProfile(
            @RequestBody UpdateUserRequestDto requestDto
    ) throws UserNotFoundException {
        return userService.updateUser(requestDto);
    }
}
