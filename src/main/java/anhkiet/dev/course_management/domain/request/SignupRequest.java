package anhkiet.dev.course_management.domain.request;
import anhkiet.dev.course_management.service.validator.Signup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Signup
public class SignupRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private long facultyId;
}
