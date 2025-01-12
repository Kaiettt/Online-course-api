package anhkiet.dev.course_management.domain.entity;

import java.time.Instant;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Instant enrollmentDate;
    
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;  // Enum for type safety
    private double score;
    private Instant created_at;
    private Instant updated_at;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course; 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student; 

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.created_at = now;
        this.updated_at = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = Instant.now();
    }
}

