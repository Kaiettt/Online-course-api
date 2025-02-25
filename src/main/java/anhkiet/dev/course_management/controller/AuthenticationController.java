package anhkiet.dev.course_management.controller;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.LoginDTO;
import anhkiet.dev.course_management.domain.request.SignupRequest;
import anhkiet.dev.course_management.domain.responce.LoginResponce;
import anhkiet.dev.course_management.domain.responce.SingupResponce;
import anhkiet.dev.course_management.error.EntityNotExistException;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.error.VerificationException;
import anhkiet.dev.course_management.service.AuthenicationService;
import anhkiet.dev.course_management.service.UserService;
import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
    public ResponseEntity<LoginResponce> login(@RequestBody LoginDTO loginDto) throws VerificationException {
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
    public ResponseEntity<SingupResponce> login(@RequestBody @Valid SignupRequest request) throws ResourceExistException{
        
        return ResponseEntity.ok().body(this.authenicationService.handleSingUp(request));
    }
    @GetMapping("/confirm")
    public ResponseEntity<SingupResponce> confirm(@RequestParam("token") String token) throws VerificationException {
      
      return ResponseEntity.ok().body(this.authenicationService.handleEmailConfirmation(token));
  }
    
  @GetMapping("/account")
  @ApiMessage("Fetch Account")
  public ResponseEntity<LoginResponce.UserLogin> getAccont(){
    String email =
        SecurityUtil.getCurrentUserLogin().isPresent()
            ? SecurityUtil.getCurrentUserLogin().get()
            : "";
    User user = this.userService.getUserByUserName(email);
    LoginResponce.UserLogin userLogin = new LoginResponce.UserLogin();
    if (user != null) {
      userLogin.setId(user.getId());
      userLogin.setFullName(user.getName());
      userLogin.setUserName(email);
    }
    return ResponseEntity.ok().body(userLogin);
  }
  @GetMapping("/refresh")
  @ApiMessage("Get Access Token")
  public ResponseEntity<LoginResponce> getAccessToken( @CookieValue(name = "refresh-token", defaultValue = "") String refresh_token)throws EntityExistsException {
    if(refresh_token == null || refresh_token.isEmpty()){
      throw new EntityExistsException("Refresh token is invalid");
    }
    LoginResponce loginResponce = this.authenicationService.getAccessToken(refresh_token);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,loginResponce.getSpringCookie().toString()).body(loginResponce);
  }
  @PostMapping("/logout")
  @ApiMessage("Log out successfully")
  public ResponseEntity<Void> LogoutRequest() throws ResourceExistException{
    String email =
        SecurityUtil.getCurrentUserLogin().isPresent()
            ? SecurityUtil.getCurrentUserLogin().get()
            : "";
    if (email == null || email.equals("")) {
      throw new ResourceExistException("Access token khong hop le");
    }
    User user = this.userService.getUserByUserName(email);
    if (user == null) {
      throw new ResourceExistException("Access token khong hop le");
    }
    this.userService.handleUpdateRefreshToken(user, null);
    ResponseCookie deleteCookies =
        ResponseCookie.from("refresh-token", null)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookies.toString()).body(null);
  }
}
