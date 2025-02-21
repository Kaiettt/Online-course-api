package anhkiet.dev.course_management.domain.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public enum Role {
    STUDENT(Set.of(
        Permission.STUDENT_READ, 
        Permission.STUDENT_CREATE, 
        Permission.STUDENT_UPDATE, 
        Permission.STUDENT_DELETE
    )),
    INSTRUCTOR(Set.of(
        Permission.INSTRUCTOR_READ, 
        Permission.INSTRUCTOR_CREATE, 
        Permission.INSTRUCTOR_UPDATE, 
        Permission.INSTRUCTOR_DELETE
    )),
    ADMIN(Set.of(
        Permission.ADMIN_READ, 
        Permission.ADMIN_CREATE, 
        Permission.ADMIN_UPDATE, 
        Permission.ADMIN_DELETE
    ));

    @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}

