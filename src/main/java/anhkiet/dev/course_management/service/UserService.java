package anhkiet.dev.course_management.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.UserRequest;
import anhkiet.dev.course_management.repository.ConfirmationTokenRepository;
import anhkiet.dev.course_management.repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final FacultyService facultyService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public UserService(ConfirmationTokenRepository confirmationTokenRepository,UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder,FacultyService facultyService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.facultyService = facultyService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    private final PasswordEncoder passwordEncoder;

    public User handleSaveRegistration(User user){
        return this.userRepository.save(user);
    }
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
    public String handleUserRegistrationWithEmail(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
            .token(token)
            .user(user)
            .expiresAt(LocalDateTime.now().plusMinutes(30)) // Set expiresAt to 30 minutes
            .build();
        this.confirmationTokenRepository.save(confirmationToken);
        return token;
    }
    
    public User getUserById(long id){
        User user = this.userRepository.findById(id).orElse(null);
        return user;
    }
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserByUserName(String username) {
        return this.userRepository.findByEmail(username);
    }
    public void updateRefreshToken(User user) {
        this.userRepository.save(user);
    }
    public void handleUpdateUser(User user) {
        this.userRepository.save(user);
    }
    public User getUserByRefreshToken(String refresh_token) {
        return this.userRepository.findByRefreshToken(refresh_token);
    }
    public void handleUpdateRefreshToken(User user,String refreshToken){
        if(user != null){
            user.setRefreshToken(refreshToken);
            this.userRepository.save(user);
        }
    }
}

