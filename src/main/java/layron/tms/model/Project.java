package layron.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import layron.tms.util.CustomStatusCoverter;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLDelete(sql = "UPDATE projects SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = FALSE")
@Table(name = "projects")
public class Project {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Getter
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Getter
    @Convert(converter = CustomStatusCoverter.class)
    private Status status;
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Getter
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
