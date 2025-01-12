package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.FacultyService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1")
public class FacultyController {
    
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }
    @PostMapping("/faculties")
    public ResponseEntity<Faculty>  createNewFalcuty(@RequestBody Faculty faculty) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.facultyService.handleSaveFaculty(faculty));
    }

    @GetMapping("/faculties")
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        return ResponseEntity.status(HttpStatus.OK).body(this.facultyService.getAll());
    }
    @GetMapping("/faculties/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable("id") String id) 
           throws InvalidIDException{
        if (!HandleNumber.isNumberic(id)) {
            throw new InvalidIDException("Invalid Id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.facultyService.getById(Long.parseLong(id)));
           }
    
}
