package anhkiet.dev.course_management.domain.responce;

import anhkiet.dev.course_management.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SingupResponce {
    private String userName;
    private String fullName;
    private Role role;
    private boolean enable;
}
