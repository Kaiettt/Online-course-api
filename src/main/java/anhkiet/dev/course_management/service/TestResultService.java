package anhkiet.dev.course_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Test;
import anhkiet.dev.course_management.domain.entity.TestResult;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.TestResultRequest;
import anhkiet.dev.course_management.repository.TestRepository;
import anhkiet.dev.course_management.repository.TestResultRepository;
import anhkiet.dev.course_management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TestResultService {
    private final TestResultRepository testResultRepository;
    private final TestService testService;
    private final UserService userService;

    public TestResult createTestResult(TestResultRequest request) { 
        Test test = this.testService.getTestById(request.getTestId());
        if(test == null){
            throw new EntityNotFoundException("Test not found");
        }
        User student = this.userService.getUserById(request.getStudentId());
        if(student == null){
            throw new EntityNotFoundException("Student not found");
        }
        TestResult testResult = TestResult.builder()
                .attempt_number(request.getAttemptNumber())
                .score(request.getScore())
                .completedAt(request.getCompletedAt())
                .status(request.getStatus())
                .totalRightAnswers(request.getTotalRightAnswers())
                .totalQuestion(request.getTotalQuestion())
                .student(student)
                .test(test)
                .highestScore(10)
                .build();

        return testResultRepository.save(testResult);
    }

    public TestResult getTestResultById(long id) {
        return testResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestResult not found"));
    }

    public List<TestResult> getTestResultHistory(Long testId, Long userId) {
        Optional<List<TestResult>> testResults = this.testResultRepository.findByTestIdAndStudentId(testId, userId);
       if(!testResults.isPresent()){
            return null;
       }
       return testResults.get();
    }
}