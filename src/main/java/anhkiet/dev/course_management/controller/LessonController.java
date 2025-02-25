package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Lesson;
import anhkiet.dev.course_management.domain.request.RequestLesson;
import anhkiet.dev.course_management.service.LessonService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
}
