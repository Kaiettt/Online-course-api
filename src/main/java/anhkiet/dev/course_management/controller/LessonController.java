package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Lesson;
import anhkiet.dev.course_management.domain.request.RequestLesson;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.LessonService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/lessons")
    @ApiMessage("Create new lesson")
    public ResponseEntity<Lesson> createNewLesson(@RequestBody RequestLesson request) throws EntityExistsException{
        return ResponseEntity.status(HttpStatus.CREATED).body(this.lessonService.createNewLesson(request));
    }
    
    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.status(HttpStatus.OK).body(this.lessonService.getAllLessons());
    }
    
    @GetMapping("/lessons/{courseId}")
    public ResponseEntity<List<Lesson>> getCourseLessons(@PathVariable("courseId") String id)   throws InvalidIDException{
        if (!HandleNumber.isNumberic(id)) {
            throw new InvalidIDException("Invalid Id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.lessonService.getAllLessonsByCourseId(Long.parseLong(id)));
    }
}
