package anhkiet.dev.course_management.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Course;
import anhkiet.dev.course_management.domain.entity.Lesson;
import anhkiet.dev.course_management.domain.request.RequestLesson;
import anhkiet.dev.course_management.repository.LessonRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseService courseService;
    public Lesson createNewLesson(RequestLesson request) throws EntityExistsException{
        Course course = this.courseService.getById(request.getCourseId());
        if(course == null){
            throw new EntityExistsException("Course not found");
        }
        Lesson lesson = Lesson.builder()
            .name(request.getName())
            .course(course)
            .sequence(request.getSequence())
            .build();
        return this.lessonRepository.save(lesson);
    }
    public Lesson getLessonById(long id){
        Optional<Lesson> lesson = this.lessonRepository.findById(id);
        if(!lesson.isPresent()){
            return null;
        }
        return lesson.get();
    }
}
