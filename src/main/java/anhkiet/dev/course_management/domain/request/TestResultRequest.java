package anhkiet.dev.course_management.domain.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestResultRequest {
    private int attemptNumber;
    private Instant completedAt;
    private double score;
    private int totalRightAnswers;
    private int totalQuestion;
    private String status;
    private long studentId;
    private long testId; 
}
