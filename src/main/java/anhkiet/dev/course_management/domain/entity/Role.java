package anhkiet.dev.course_management.domain.entity;

import java.time.Instant;
import java.util.List;
import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private boolean active;
    private Instant created_at;
    private Instant updated_at;
    private String updatedBy;
    private String createdBy;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permission", 
        joinColumns = @JoinColumn(name = "role_id"), 
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permisison> permissions;
    @PrePersist
    public void prePersist() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
            ? SecurityUtil.getCurrentUserLogin().get()
            : "";
        Instant now = Instant.now();
        this.created_at = now;
        this.createdBy = email;
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = Instant.now();
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
            ? SecurityUtil.getCurrentUserLogin().get()
            : "";
        this.updatedBy = email;
    }
}
