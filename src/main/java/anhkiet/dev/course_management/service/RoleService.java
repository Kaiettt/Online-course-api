package anhkiet.dev.course_management.service;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.repository.RoleRepository;
@Service
public class RoleService {
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private final RoleRepository roleRepository;

    public Role getRoleById(long id){

        Optional<Role> role = this.roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }
        return null;
    }
}
