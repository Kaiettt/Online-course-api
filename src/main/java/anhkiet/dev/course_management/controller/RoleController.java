package anhkiet.dev.course_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Permisison;
import anhkiet.dev.course_management.domain.entity.Role;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.RoleService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/roles")
    @ApiMessage("Create new role")
    public ResponseEntity<Role> createNewRole(@RequestBody Role roleRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.handleSaveRole(roleRequest));
    }
    @PutMapping("/roles")
    @ApiMessage("Update new role")
    public ResponseEntity<Role> UpdateRole(@RequestBody Role roleRequest) throws EntityExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.handleUpdateRole(roleRequest));
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete role")
    public ResponseEntity<Void> UpdateRole(@PathVariable("id") String id) throws EntityExistsException,InvalidIDException{
        if(!HandleNumber.isNumberic(id)){
            throw new InvalidIDException("Invalid id format");
        }
        this.roleService.handleDeleteRole(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
