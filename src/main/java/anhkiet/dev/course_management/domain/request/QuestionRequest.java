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
public class QuestionRequest {
    private long testId;
    private String questionTitle;
    private String answer;
    private List<String>questionOptions;
}
