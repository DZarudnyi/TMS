package layron.tms.dto.label;

import jakarta.validation.constraints.NotBlank;

public record CreateLabelRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String color
) {
}
