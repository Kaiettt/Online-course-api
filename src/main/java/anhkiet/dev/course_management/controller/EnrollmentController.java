package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Enrollment;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.EnrollmentRequest;
import anhkiet.dev.course_management.domain.request.UserRequest;
import anhkiet.dev.course_management.service.EnrollmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollments")
    @ApiMessage("Create new Enrollment")
    public ResponseEntity<Enrollment> createNewEnrollment(@RequestBody EnrollmentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.enrollmentService.handleSaveEnrollment(request));
    }
    
}
