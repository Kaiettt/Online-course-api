package anhkiet.dev.course_management.domain.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Instant start_date;
    private Instant end_date;
    private int capacity;
    
    @Enumerated(EnumType.STRING)
    private CourseStatus status;  // Enum for type safety
    private Instant created_at;
    private Instant updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"faculty", "role"})
    private User instructor;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonIgnoreProperties({"updated_at","created_at","bio","telephone"})
    private Faculty faculty;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY) 
    @JsonIgnore
    private List<Enrollment> enrollments;

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