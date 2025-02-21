package anhkiet.dev.course_management.config;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailServiceImpl  implements UserDetailsService {
    private UserService userService;
    public UserDetailServiceImpl(UserService userService){
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        anhkiet.dev.course_management.domain.entity.User user = this.userService.getUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("Username/Password not valid");
        }
        User user_detail = new User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
        return user_detail;
    }
}
