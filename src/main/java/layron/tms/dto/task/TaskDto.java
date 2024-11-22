package layron.tms.dto.task;

import java.time.LocalDate;
import layron.tms.model.Priority;
import layron.tms.model.Status;

public record TaskDto(
        Long id,
        String name,
        Priority priority,
        Status status,
        LocalDate dueDate,
        Long projectId,
        Long assigneeId
) {
}
