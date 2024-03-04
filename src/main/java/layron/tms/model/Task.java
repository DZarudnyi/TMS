package layron.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@SQLDelete(sql = "UPDATE tasks SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = FALSE")
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Priority priority;
    private Status status;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column(name = "project_id", nullable = false)
    private Long projectdId;
    @Column(name = "asignee_id", nullable = false)
    private Long asigneeId;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
