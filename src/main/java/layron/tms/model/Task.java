package layron.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Enumerated(value = EnumType.STRING)
    private Priority priority;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
