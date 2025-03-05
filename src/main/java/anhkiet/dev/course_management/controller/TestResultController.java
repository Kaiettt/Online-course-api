package anhkiet.dev.course_management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import anhkiet.dev.course_management.domain.request.TestResultRequest;
import anhkiet.dev.course_management.domain.entity.TestResult;
import anhkiet.dev.course_management.service.TestResultService;

@RestController
@RequestMapping("/api/v1/test-results")
@AllArgsConstructor
public class TestResultController {
    private final TestResultService testResultService;

    @PostMapping
    public ResponseEntity<TestResult> createTestResult(@RequestBody TestResultRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testResultService.createTestResult(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResult> getTestResult(@PathVariable long id) {
        return ResponseEntity.ok(testResultService.getTestResultById(id));
    }

    @GetMapping("/{testId}/{userId}")
    public ResponseEntity<List<TestResult>> getTestResultHistory(@PathVariable Long testId,
    @PathVariable Long userId) {        return ResponseEntity.ok(testResultService.getTestResultHistory(testId,userId));
    }
}