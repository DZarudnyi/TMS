package layron.tms.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Size(min = 4, max = 35)
        String username,
        @NotBlank
        @Size(min = 4, max = 35)
        String password
) {
}
