package anhkiet.dev.course_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import anhkiet.dev.course_management.domain.entity.Course;
import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.responce.ResultPaginationDTO;
import anhkiet.dev.course_management.domain.responce.ResultPaginationDTO.Meta;
import anhkiet.dev.course_management.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final FacultyService facultyService;
    public CourseService ( CourseRepository courseRepository,UserService userService,FacultyService facultyService){
            this.courseRepository = courseRepository;
            this.userService = userService;
            this.facultyService = facultyService;
    }

    public Course handleSaveCourse(Course course) {
        User instructor = this.userService.getUserById(course.getInstructor().getId());
        Faculty faculty = this.facultyService.getFacultyById(course.getFaculty().getId());
        Course newCourse = Course.builder()
            .capacity(course.getCapacity())
            .description(course.getDescription())
            .faculty(faculty)
            .instructor(instructor)
            .start_date(course.getStart_date())
            .name(course.getName())
            .status(course.getStatus())
            .end_date(course.getEnd_date())
            .build();
        return this.courseRepository.save(newCourse);
    }

    public ResultPaginationDTO getAll(Specification spec,Pageable pageable) {
        Page<Course> pageCourse = this.courseRepository.findAll(spec,pageable);
        
        ResultPaginationDTO.Meta meta = Meta.builder()
            .page(pageable.getPageNumber()+1)
            .pageSize(pageable.getPageSize())
            .pages(pageCourse.getTotalPages())
            .total(pageCourse.getTotalElements())
            .build();
        ResultPaginationDTO res = ResultPaginationDTO.builder()
            .meta(meta)
            .result(pageCourse.getContent())
            .build();

        return res;
    }

    



    public Course getById(long id) {
        Optional<Course> course = this.courseRepository.findById(id);
        if(course.isPresent()){
            return course.get();
        }
        return null;
    }


}
