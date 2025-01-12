package anhkiet.dev.course_management.domain.request;

import anhkiet.dev.course_management.domain.entity.Faculty;
import anhkiet.dev.course_management.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String name; 
    private String email; 
    private String password; 
    private String profilePicture; 
    private Role role;
    private Faculty faculty;
    // public UserRequest(String name, String email, String password, String profilePicture, Role role) {
    //     this.name = name;
    //     this.email = email;
    //     this.password = password;
    //     this.profilePicture = profilePicture;
    //     this.role = role;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }

    // public String getProfilePicture() {
    //     return profilePicture;
    // }

    // public void setProfilePicture(String profilePicture) {
    //     this.profilePicture = profilePicture;
    // }

    // public Role getRole() {
    //     return role;
    // }

    // public void setRole(Role role) {
    //     this.role = role;
    // }
}
