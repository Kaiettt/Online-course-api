package anhkiet.dev.course_management.service;


import java.time.LocalDateTime;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.SignupRequest;
import anhkiet.dev.course_management.domain.responce.LoginResponce;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.error.VerificationException;
import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
@Service
@AllArgsConstructor
public class AuthenicationService {
    private final UserService userService;
    private final FacultyService facultyService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final EmailServiceImpl emailServiceImpl;
    private ConfirmationService confirmationService;
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

        this.emailServiceImpl.send(request.getEmail(), user.getName(),link);

        // return link;
    }
    public LoginResponce handleLoginResponce(Authentication authentication, String userName) {

        User user = this.userService.getUserByUserName(userName);
        LoginResponce.UserLogin userResponce = new LoginResponce.UserLogin();
        userResponce.setUserName(userName);
        userResponce.setFullName(user.getName());
        String accessToken = this.securityUtil.createToken(authentication,userResponce);
        LoginResponce loginResponce = new LoginResponce();
        loginResponce.setUser(userResponce);
        loginResponce.setAccessToken(accessToken);

        String refreshToken = this.securityUtil.createRefreshToken(authentication, userResponce);
        user.setRefreshToken(refreshToken);
        this.userService.updateRefreshToken(user);


        ResponseCookie springCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtil.jwtRefreshTokenExpiration)
                .build();
        loginResponce.setSpringCookie(springCookie);
        return loginResponce;
    }

    @Transactional
    public void handleEmailConfirmation(String verificationToken) throws VerificationException{
        ConfirmationToken confirmationToken = this.confirmationService.getConfirmationByToken(verificationToken).orElseThrow(
            () -> new VerificationException("token is not found.")
        );
        if(confirmationToken.getConfirmedAt() != null){
            throw new VerificationException("email is already confirmed");
        }
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new VerificationException("email is already expired");
        }
        this.confirmationService.ConfirmToken(verificationToken);
        User user = this.userService.getUserById(confirmationToken.getUser().getId());
        user.setEnabled(true);
        this.userService.handleUpdateUser(user);
    }
}
