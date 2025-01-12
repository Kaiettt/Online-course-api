package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import anhkiet.dev.course_management.domain.entity.Course;
import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.responce.ResultPaginationDTO;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.CourseService;
import anhkiet.dev.course_management.service.FacultyService;
import anhkiet.dev.course_management.service.UserService;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createNewCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.handleSaveCourse(course));
    }

    @GetMapping("/courses")
    public ResponseEntity<ResultPaginationDTO> getAllFaculties(@Filter Specification<Course> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.courseService.getAll(spec,pageable));
    }
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") String id) 
           throws InvalidIDException{
        if (!HandleNumber.isNumberic(id)) {
            throw new InvalidIDException("Invalid Id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.courseService.getById(Long.parseLong(id)));
           }
    
    
}
