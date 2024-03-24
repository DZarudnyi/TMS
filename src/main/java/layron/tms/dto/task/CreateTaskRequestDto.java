package layron.tms.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record CreateTaskRequestDto(
        @NotBlank
        String name,
        @NotNull
        @Positive
        int priority,
        @NotNull
        LocalDate dueDate,
        @NotNull
        @Positive
        Long assigneeId
) {
}
