package anhkiet.dev.course_management.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import anhkiet.dev.course_management.common.Constants;
import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.UserRequest;
import anhkiet.dev.course_management.error.EmailAlreadyExistsException;
import anhkiet.dev.course_management.repository.ConfirmationTokenRepository;
import anhkiet.dev.course_management.repository.UserRepository;
import java.util.Random;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FacultyService facultyService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public UserService(ConfirmationTokenRepository confirmationTokenRepository,UserRepository userRepository, PasswordEncoder passwordEncoder,FacultyService facultyService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.facultyService = facultyService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }


    public User handleSaveRegistration(User user){
        return this.userRepository.save(user);
    }
    public User createNewUser(UserRequest request){
        if(this.userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS);
        }
        User user  =
                User.builder()
                 .name(request.getName())
                 .email(request.getEmail())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .profilePicture(request.getProfilePicture())
                 .role(request.getRole())
                 .faculty(this.facultyService.getFacultyById(request.getFaculty().getId()))
                 .build();
        User newUser = this.userRepository.save(user);
        return newUser;
    }
    public long handleUserRegistrationWithEmail(User user) {
        Random random = new Random();
        long token = 100000 + random.nextInt(900000);
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
        Optional<User> user = this.userRepository.findByEmail(username);
        if(user.isPresent()){
            return user.get();
        }
        return null;

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

