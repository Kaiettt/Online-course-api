package anhkiet.dev.course_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import anhkiet.dev.course_management.domain.entity.Lesson;
import java.util.List;
public interface LessonRepository  extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson>{
    @Query("SELECT l FROM Lesson l WHERE l.course.id = :id")
    List<Lesson> findAllByCourseId(@Param("id") long id);
}
