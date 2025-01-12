package anhkiet.dev.course_management.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.SignupRequest;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenicationService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final FacultyService facultyService;
    private final PasswordEncoder passwordEncoder;


    public User handleSingUp(SignupRequest request) throws ResourceExistException,EntityExistsException {
        if(this.userService.getUserByUserName(request.getEmail()) != null){
            throw new ResourceExistException("Email already exist");
        }
        Faculty dbFaculty = this.facultyService.getFacultyById(request.getFacultyId());
        if(dbFaculty == null){
            throw new EntityExistsException("Faculty not found");
        }
        Role dbRole = new Role();
        dbRole.setId(2);
        dbRole.setName("STUDENT"); 
        User user = User.builder()
            .faculty(dbFaculty)
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getFirstName() + " " + request.getLastName())
            .email(request.getEmail())
            .role(dbRole)
            .build();
        return this.userRepository.save(user);
    }

    
}
