package anhkiet.dev.course_management;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.UserRequest;
import anhkiet.dev.course_management.error.EmailAlreadyExistsException;
import anhkiet.dev.course_management.repository.ConfirmationTokenRepository;
import anhkiet.dev.course_management.repository.UserRepository;
import anhkiet.dev.course_management.service.FacultyService;
import anhkiet.dev.course_management.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
@SpringBootTest
public class UserServiceTests {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired private UserService userService;

    @Test
  public void createNewUser_requestWithExistingEmail_throwsException() {
    String existingEmail = "existing@gmail.com";
    UserRequest request =
        UserRequest.builder()
        .email(existingEmail)
        .name("Full Name")
        .password("password")
        .role(Role.STUDENT)
        .faculty(null)
        .profilePicture("")
        .build();

    User existingUser =
        User.builder()
            .name("Full Name")
            .email(existingEmail)
            .password("encodedPassword")
            .role(Role.STUDENT)
            .faculty(null)
            .profilePicture("")
            .build();
    when(userRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingUser));
    assertThrows(EmailAlreadyExistsException.class, () -> userService.createNewUser(request));
  }

  @Test
  public void createNewUser_giveSuitableRequest_CreatesUser(){
    Faculty testFaculty = Faculty.builder()
      .id(1)
      .name("Name")
      .build();
    UserRequest request =
        UserRequest.builder()
        .email("test@gmail.com")
        .name("Full Name")
        .password("password")
        .profilePicture("")
        .role(Role.STUDENT)
        .faculty(testFaculty)
        .build();
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

      User newUser = User.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(encodedPassword)
        .profilePicture(request.getProfilePicture())
        .role(Role.STUDENT)
        .faculty(testFaculty)
        .build();
      User expectedUser = User.builder()
        .id(1)
        .name(newUser.getName())
        .email(newUser.getEmail())
        .password(encodedPassword)
        .role(Role.STUDENT)
        .faculty(testFaculty)
        .profilePicture(newUser.getProfilePicture())
        .build();
      when(userRepository.save(newUser)).thenReturn(expectedUser);

      User userResponce = userService.createNewUser(request);
      assertNotNull(userResponce);
      assertEquals(expectedUser.getId(), userResponce.getId());
      assertEquals(expectedUser.getName(), userResponce.getName());
      assertEquals(expectedUser.getEmail(), userResponce.getEmail());
      assertEquals(expectedUser.getFaculty(), userResponce.getFaculty());
      assertEquals(expectedUser.getFaculty(), userResponce.getFaculty());
      assertEquals(expectedUser.getProfilePicture(), userResponce.getProfilePicture());
      assertEquals(expectedUser.getRole(), userResponce.getRole());
  }
}