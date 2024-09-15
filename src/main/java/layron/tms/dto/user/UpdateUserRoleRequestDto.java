package layron.tms.dto.user;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record UpdateUserRoleRequestDto(
        @NotNull
        Set<Long> roleIds
) {
}
