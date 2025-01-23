package anhkiet.dev.course_management.domain.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "permisisons")
public class Permisison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String apiPath;
    private String method;
    private String module;
    private Instant created_at;
    private Instant updated_at;
    private String updatedBy;
    private String createdBy;
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;
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
