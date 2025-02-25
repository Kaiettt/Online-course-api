package anhkiet.dev.course_management.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestLesson {
    private String name;
    private int sequence;
    private long courseId;
}
