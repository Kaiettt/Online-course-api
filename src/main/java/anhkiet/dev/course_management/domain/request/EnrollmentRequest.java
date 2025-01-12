package anhkiet.dev.course_management.domain.request;

import anhkiet.dev.course_management.domain.entity.Course;
import anhkiet.dev.course_management.domain.entity.EnrollmentStatus;
import anhkiet.dev.course_management.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollmentRequest {
    private User user;
    private Course course;
    private EnrollmentStatus enrollmentStatus;
}
