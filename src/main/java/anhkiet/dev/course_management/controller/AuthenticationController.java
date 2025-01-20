package anhkiet.dev.course_management.controller;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.LoginDTO;
import anhkiet.dev.course_management.domain.request.SignupRequest;
import anhkiet.dev.course_management.domain.responce.LoginResponce;
import anhkiet.dev.course_management.error.EntityNotExistException;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.service.AuthenicationService;
import anhkiet.dev.course_management.service.UserService;
import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenicationService authenicationService;
    private final  UserService userService;
    public AuthenticationController( UserService userService,AuthenicationService authenicationService,AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenicationService = authenicationService;
      this.userService = userService;
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

        LoginResponce loginResponce = this.authenicationService.handleLoginResponce(authentication,loginDto.getUsername());
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,loginResponce.getSpringCookie().toString()).body(loginResponce);
    }

    @PostMapping("/signup")
    @ApiMessage("Singup succesfully")
    public ResponseEntity<Void> login(@RequestBody @Valid SignupRequest request) throws ResourceExistException,EntityNotExistException{
        this.authenicationService.handleSingUp(request);
        return ResponseEntity.ok().body(null);
    }
  @GetMapping("/auth/account")
  @ApiMessage("Fetch Account")
  public ResponseEntity<LoginResponce.UserLogin> getAccont(){
    String email =
        SecurityUtil.getCurrentUserLogin().isPresent()
            ? SecurityUtil.getCurrentUserLogin().get()
            : "";
    User user = this.userService.getUserByUserName(email);
    LoginResponce.UserLogin userLogin = new LoginResponce.UserLogin();
    if (user != null) {
      userLogin.setFullName(user.getName());
      userLogin.setUserName(email);
    }
    return ResponseEntity.ok().body(userLogin);
  }
    
}
