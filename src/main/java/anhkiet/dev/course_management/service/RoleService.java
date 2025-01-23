package anhkiet.dev.course_management.service;
import java.security.Permission;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Permisison;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.repository.RoleRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class RoleService {
    private final PermissionService permissionService;
    private final RoleRepository roleRepository;

    public Role getRoleById(long id){

        Optional<Role> role = this.roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }
        return null;
    }

    public Role handleSaveRole(Role roleRequest) {
        List<Long> id = roleRequest.getPermissions().stream()
                        .map(permission -> permission.getId()) 
                        .collect(Collectors.toList());
        List<Permisison> permissions = this.permissionService.getPermissionsById(id);
        Role role = Role.builder()
            .active(true)
            .description(roleRequest.getDescription())
            .name(roleRequest.getName())
            .permissions(permissions)
            .build();
        return this.roleRepository.save(role);
    }

    public Role handleUpdateRole(Role roleRequest) throws EntityExistsException{
        Role role = this.getRoleById(roleRequest.getId());
        if(role == null){
            throw new EntityExistsException("Role not found");
        }
        if(roleRequest.getPermissions() != null ){
            List<Long> id = roleRequest.getPermissions().stream()
                        .map(permission -> permission.getId()) 
                        .collect(Collectors.toList());
            List<Permisison> permissions = this.permissionService.getPermissionsById(id);
            role.setPermissions(permissions);
        }
        return this.roleRepository.save(role);
    }

    public void handleDeleteRole(long id)  throws EntityExistsException{
        if(this.getRoleById(id) == null){
            throw new EntityExistsException("Role not found");
        }
        this.roleRepository.deleteById(id);
    }
}
