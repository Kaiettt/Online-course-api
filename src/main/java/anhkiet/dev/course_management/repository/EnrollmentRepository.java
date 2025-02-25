package anhkiet.dev.course_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import anhkiet.dev.course_management.domain.entity.Enrollment;

public interface EnrollmentRepository  extends JpaRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment>{
    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseID AND e.student.id = :userID")
    Optional<Enrollment> findByCourseIdAndUserId(@Param("courseID") long courseID, @Param("userID") long userID);
}
