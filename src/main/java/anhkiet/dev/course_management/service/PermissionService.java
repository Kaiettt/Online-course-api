package anhkiet.dev.course_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Permisison;
import anhkiet.dev.course_management.repository.PermissionRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    public Permisison handleSavePermission(Permisison request){
        Permisison newPermission = Permisison.builder()
            .apiPath(request.getApiPath())
            .method(request.getMethod())
            .module(request.getModule())
            .name(request.getName())
            .build();
        return this.permissionRepository.save(newPermission);
    }
    public List<Permisison> getPermissionsById(List<Long>id){
        return this.permissionRepository.findAllById(id);
    }
    public void handleDeletePermission(long id) {
        Optional<Permisison> optinalPermisison = this.permissionRepository.findById(id);
        if(!optinalPermisison.isPresent()){
            throw new EntityExistsException("Permission not found");
        }
        Permisison permisison = optinalPermisison.get();
        permisison.getRoles().forEach(role -> role.getPermissions().remove(permisison));
        permisison.getRoles().clear();
        
        this.permissionRepository.delete(permisison);
    }
}
