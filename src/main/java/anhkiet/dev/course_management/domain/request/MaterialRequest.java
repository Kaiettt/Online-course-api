package anhkiet.dev.course_management.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaterialRequest {
    private String uri;
    private String name;
    private String fileName;
    private String folder;

    private long lessonId;
}
