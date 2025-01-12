package anhkiet.dev.course_management.domain.responce;

public class LoginResponce {
    private String username;
    private String accessToken;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public LoginResponce(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
}
