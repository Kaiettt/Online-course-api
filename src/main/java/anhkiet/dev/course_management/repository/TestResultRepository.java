package anhkiet.dev.course_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import anhkiet.dev.course_management.domain.entity.TestResult;
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    Optional<List<TestResult>> findByTestIdAndStudentId(Long testId, Long userId);

}