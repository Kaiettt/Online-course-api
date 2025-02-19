package anhkiet.dev.course_management.domain.request;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
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
public class UserRequest {
    private String name; 
    private String email; 
    private String password; 
    private String profilePicture; 
    private Role role;
    private Faculty faculty;
}
