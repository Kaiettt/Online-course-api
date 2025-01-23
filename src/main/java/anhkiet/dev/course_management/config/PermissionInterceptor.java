package anhkiet.dev.course_management.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import anhkiet.dev.course_management.domain.entity.Permisison;
import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.service.UserService;
import anhkiet.dev.course_management.util.SecurityUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PermissionInterceptor implements HandlerInterceptor{
    @Autowired
    private UserService userService;
     @Override 
    public boolean preHandle( 
            HttpServletRequest request, 
            HttpServletResponse response, Object handler) 
            throws Exception {  
                String requestURI = request.getRequestURI(); 
                String httpMethod = request.getMethod();          
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                    ? SecurityUtil.getCurrentUserLogin().get()
                    : "";
        User user = this.userService.getUserByUserName(email);
        if (user == null) {
            throw new EntityExistsException("User not found");
        }
        List<Permisison> permissions = user.getRole().getPermissions();
        
        boolean isPermitted = false;
        for (Permisison permisison : permissions) {
            if(requestURI.contains(permisison.getApiPath()) && httpMethod.equals(permisison.getMethod())){
                    isPermitted = true;
                    break;
            }
        }
        if(!isPermitted){
            throw new AccessDeniedException("Access denied");
        }
        return isPermitted;
    } 
}
