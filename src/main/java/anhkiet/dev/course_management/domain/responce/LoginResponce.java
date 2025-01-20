package anhkiet.dev.course_management.domain.responce;

import org.springframework.http.ResponseCookie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponce {
    private String accessToken;
    private UserLogin user;
    @JsonIgnore
    private ResponseCookie springCookie;
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserLogin{
        private String userName;
        private String fullName;
    }
    
}
