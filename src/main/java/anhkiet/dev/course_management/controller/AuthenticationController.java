package anhkiet.dev.course_management.controller;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.LoginDTO;
import anhkiet.dev.course_management.domain.request.SignupRequest;
import anhkiet.dev.course_management.domain.responce.LoginResponce;
import anhkiet.dev.course_management.error.EntityNotExistException;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.service.AuthenicationService;
import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private AuthenicationService authenicationService;
    public AuthenticationController(AuthenicationService authenicationService,AuthenticationManagerBuilder authenticationManagerBuilder,SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.authenicationService = authenicationService;

    }

    @PostMapping("/login")
    @ApiMessage("Login succesfully")
    public ResponseEntity<LoginResponce> login(@RequestBody LoginDTO loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = this.securityUtil.createToken(authentication);
        LoginResponce loginResponce = new LoginResponce(loginDto.getUsername(), accessToken);
        return ResponseEntity.ok().body(loginResponce);
    }

    @PostMapping("/signup")
    @ApiMessage("Singup succesfully")
    public ResponseEntity<User> login(@RequestBody @Valid SignupRequest request) throws ResourceExistException,EntityNotExistException{
        return ResponseEntity.ok().body(this.authenicationService.handleSingUp(request));
    }
}
