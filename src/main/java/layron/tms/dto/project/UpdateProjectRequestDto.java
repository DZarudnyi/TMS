package layron.tms.dto.project;

import java.time.LocalDate;

public record UpdateProjectRequestDto(
        String name,
        String description,
        LocalDate endDate
) {
}
