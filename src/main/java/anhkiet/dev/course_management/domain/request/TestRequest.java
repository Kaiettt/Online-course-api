package anhkiet.dev.course_management.domain.request;

import java.util.List;

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
public class TestRequest {
    private long lessonId;
    private String title;
    private int duration;
    private int attempsAllowed;
    private  List<QuestionRequest>question;
}
