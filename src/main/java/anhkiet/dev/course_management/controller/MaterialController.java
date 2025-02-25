package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Material;
import anhkiet.dev.course_management.domain.request.MaterialRequest;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.service.MaterialService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MaterialController {
    private final MaterialService materialService;

     @PostMapping("/materials")
    @ApiMessage("Create new material")
    public ResponseEntity<Material> createNewLesson(@RequestBody MaterialRequest request) throws ResourceExistException{
        return ResponseEntity.status(HttpStatus.CREATED).body(this.materialService.createNewMaterial(request));
    }
    
}
