package layron.tms.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record UpdateTaskRequestDto(
        @NotBlank
        String name,
        @NotNull
        @Positive
        int priority,
        @NotNull
        @Positive
        int status,
        @NotNull
        LocalDate dueDate,
        @NotNull
        @Positive
        Long assigneeId
) {
}
