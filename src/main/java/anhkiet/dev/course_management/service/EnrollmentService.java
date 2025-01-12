package anhkiet.dev.course_management.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Course;
import anhkiet.dev.course_management.domain.entity.Enrollment;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.EnrollmentRequest;
import anhkiet.dev.course_management.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final UserService userService;
    public EnrollmentService(EnrollmentRepository enrollmentRepository,CourseService courseService,UserService userService){
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.userService = userService;
    }
    public Enrollment handleSaveEnrollment(EnrollmentRequest request) {
        User dbUser = this.userService.getUserById(request.getUser().getId());
        Course dbCoures = this.courseService.getById(request.getCourse().getId());
        Enrollment enrollment = Enrollment.builder()
            .student(dbUser)
            .course(dbCoures)
            .enrollmentDate(Instant.now())
            .status(request.getEnrollmentStatus())
            .build();
        return this.enrollmentRepository.save(enrollment);
    }



}
