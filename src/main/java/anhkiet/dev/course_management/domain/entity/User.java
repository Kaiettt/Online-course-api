package anhkiet.dev.course_management.domain.entity;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name; 
    private String email;
    @JsonIgnore
    private String password; 
    private String profilePicture;
    private boolean enabled;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
    
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY) 
    @JsonIgnore
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonIgnore
    private Faculty faculty;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY) 
    @JsonIgnore
    private List<Enrollment> enrollments;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

    @Override
    public String getUsername() {
       return email;
    } 


    

}
