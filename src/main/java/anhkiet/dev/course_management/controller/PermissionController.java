package anhkiet.dev.course_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Permisison;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.PermissionService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;
    @PostMapping("/permissions")
    @ApiMessage("Create new permission")
    public ResponseEntity<Permisison> createNewPermission(@RequestBody Permisison permisisonRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.handleSavePermission(permisisonRequest));
    }
    
    @DeleteMapping("/permissions/{id}")
    @ApiMessage("Delete permission")
    public ResponseEntity<Void> UpdatePermission(@PathVariable("id") String id) throws EntityExistsException,InvalidIDException{
        if(!HandleNumber.isNumberic(id)){
            throw new InvalidIDException("Invalid id format");
        }
        this.permissionService.handleDeletePermission(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
