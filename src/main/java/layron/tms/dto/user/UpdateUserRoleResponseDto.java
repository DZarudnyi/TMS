package layron.tms.dto.user;

import java.util.Set;

public record UpdateUserRoleResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        Set<Long> roleIds
) {
}
