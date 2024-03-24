package layron.tms.dto.label;

import jakarta.validation.constraints.NotBlank;

public record UpdateLabelRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String color
) {
}
