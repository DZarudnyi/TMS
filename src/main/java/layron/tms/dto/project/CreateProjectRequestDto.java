package layron.tms.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateProjectRequestDto(
        @NotBlank
        String name,
        String description,
        @NotNull
        LocalDate endDate
) {
}
