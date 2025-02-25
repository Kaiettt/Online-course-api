package anhkiet.dev.course_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import anhkiet.dev.course_management.domain.entity.Course;
import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.responce.CourseResponce;
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

    public CourseResponce handleSaveCourse(Course course) {
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
        
        course  = this.courseRepository.save(newCourse);
        CourseResponce courseResponce = CourseResponce.builder()
            .instructorEmail(course.getInstructor().getEmail())
            .instructorName(course.getInstructor().getName())
            .updatedAt(course.getUpdated_at())
            .createdAt(course.getCreated_at())
            .status(course.getStatus())
            .capacity(course.getCapacity())
            .endDate(course.getEnd_date())
            .startDate(course.getStart_date())
            .description(course.getDescription())
            .facultyName(course.getFaculty().getName())
            .name(course.getName())
            .id(course.getId())
            .build();
        return courseResponce;
    }

    public ResultPaginationDTO getAll(Specification spec,Pageable pageable) {
        Page<Course> pageCourse = this.courseRepository.findAll(spec,pageable);
        
        ResultPaginationDTO.Meta meta = Meta.builder()
            .page(pageable.getPageNumber()+1)
            .pageSize(pageable.getPageSize())
            .pages(pageCourse.getTotalPages())
            .total(pageCourse.getTotalElements())
            .build();
        
        List<CourseResponce> courseList = new ArrayList<>();
        for(Course course : pageCourse.getContent()){
            CourseResponce courseResponce = CourseResponce.builder()
            .instructorEmail(course.getInstructor().getEmail())
            .instructorName(course.getInstructor().getName())
            .updatedAt(course.getUpdated_at())
            .createdAt(course.getCreated_at())
            .status(course.getStatus())
            .capacity(course.getCapacity())
            .endDate(course.getEnd_date())
            .startDate(course.getStart_date())
            .description(course.getDescription())
            .name(course.getName())
            .facultyName(course.getFaculty().getName())
            .id(course.getId())
            .build();
            courseList.add(courseResponce);
        }
        ResultPaginationDTO res = ResultPaginationDTO.builder()
            .meta(meta)
            .result(courseList)
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
