package anhkiet.dev.course_management.service;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }
    public Faculty handleSaveFaculty(Faculty faculty) {
        Faculty newFaculty = Faculty.builder()
            .bio(faculty.getBio())
            .telephone(faculty.getTelephone())
            .name(faculty.getName())
            .build();
        return this.facultyRepository.save(newFaculty);
    }
    public Faculty getFacultyById(long id) {
        Optional<Faculty> faculty = this.facultyRepository.findById(id);
        if(faculty.isPresent()){
            return faculty.get();
        }
        return null;
    }
    public List<Faculty> getAll() {
        return this.facultyRepository.findAll();
    }
    public Faculty getById(long id) {
        Optional<Faculty> optionalFaculty = this.facultyRepository.findById(id);
        if(optionalFaculty.isPresent()){
            return optionalFaculty.get();
        }
        return null;
    }
    
}
