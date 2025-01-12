package anhkiet.dev.course_management.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.UserRequest;
import anhkiet.dev.course_management.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final FacultyService facultyService;
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder,FacultyService facultyService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.facultyService = facultyService;
    }

    private final PasswordEncoder passwordEncoder;
    public User handleSaveUser(UserRequest request) {
        Role role = this.roleService.getRoleById(request.getRole().getId());
        Faculty faculty = this.facultyService.getFacultyById(request.getFaculty().getId());
        if(role != null){
            User user  =
                User.builder()
                 .name(request.getName())
                 .email(request.getEmail())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .profilePicture(request.getProfilePicture())
                 .role(role)
                 .faculty(faculty)
                 .build();
            User newUser = this.userRepository.save(user);
            // return UserMapper.INSTANCE.userToUserResponse(newUser);
            return newUser;
        }
        return null;
    }
    public User getUserById(long id){
        User user = this.userRepository.findById(id).orElse(null);
        // return UserMapper.INSTANCE.userToUserResponse(user);
        return user;
    }
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserByUserName(String username) {
        return this.userRepository.findByEmail(username);
    }
}

