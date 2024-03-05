package layron.tms.mapper;

import layron.tms.config.MapperConfig;
import layron.tms.dto.user.UpdateUserRoleRequestDto;
import layron.tms.dto.user.UpdateUserRoleResponseDto;
import layron.tms.dto.user.UserDto;
import layron.tms.dto.user.UserRegistrationResponseDto;
import layron.tms.model.Role;
import layron.tms.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface UserMapper {
    UserRegistrationResponseDto toUserResponse(User user);

    UserDto toDto(User user);

    @Mapping(target = "roles", source = "roleIds")
    User toEntity(UpdateUserRoleRequestDto requestDto);

    UpdateUserRoleResponseDto toDtoWithRoles(User user);

    @AfterMapping
    default UpdateUserRoleResponseDto setRoleIds(@MappingTarget UpdateUserRoleResponseDto responseDto, User user) {
        if (user.getRoles() == null) {
            return responseDto;
        }
        return new UpdateUserRoleResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream()
                        .map(Role::getId)
                        .collect(Collectors.toSet())
        );
    }

    default Set<Role> mapSetOfIdsToRoles(Set<Long> ids) {
        if (ids == null) {
            return new HashSet<>();
        }
        return ids.stream()
                .map(id -> {
                    Role role = new Role();
                    role.setId(id);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
