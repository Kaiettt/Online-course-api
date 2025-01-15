package anhkiet.dev.course_management.service;


import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.SignupRequest;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.error.VerificationException;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
@Service
@AllArgsConstructor
public class AuthenicationService {
    private final UserService userService;
    private final FacultyService facultyService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    // private final ConfirmationService confirmationService;
    public void handleSingUp(SignupRequest request) throws ResourceExistException,EntityExistsException {
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
            .enabled(false)
            .build();
        this.userService.handleSaveRegistration(user);
        String verificationToken = this.userService.handleUserRegistrationWithEmail(user);

        String link = "http://localhost:9409/api/v1/auth/confirm?token=" + verificationToken;

        this.emailService.send(verificationToken, user.getName(),link);

        // return link;
    }

    // @Transactional
    // public void handleEmailConfirmation(String verificationToken) throws VerificationException{
    //     ConfirmationToken confirmationToken = this.confirmationService.getConfirmationByToken(verificationToken).orElseThrow(
    //         () -> new VerificationException("token is not found.")
    //     );
    //     if(confirmationToken.getConfirmedAt() != null){
    //         throw new VerificationException("email is already confirmed");
    //     }
    //     if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
    //         throw new VerificationException("email is already expired");
    //     }

    // }
}
