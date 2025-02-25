package anhkiet.dev.course_management.domain.responce;

import java.time.Instant;

import anhkiet.dev.course_management.domain.entity.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CourseResponce {
    private long id;
    private String name;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private int capacity;
    private CourseStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private String instructorName;
    private String facultyName;
    private String instructorEmail; 
}
