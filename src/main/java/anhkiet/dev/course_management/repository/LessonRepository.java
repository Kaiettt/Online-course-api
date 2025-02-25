package anhkiet.dev.course_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import anhkiet.dev.course_management.domain.entity.Lesson;

public interface LessonRepository  extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson>{
    
}
