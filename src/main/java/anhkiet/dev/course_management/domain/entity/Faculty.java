package anhkiet.dev.course_management.domain.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String telephone;
    private String bio;
    private Instant created_at;
    private Instant updated_at;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY) 
    @JsonIgnore
    private List<Course> courses; 
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

