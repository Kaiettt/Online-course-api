package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.domain.entity.Test;
import anhkiet.dev.course_management.domain.request.TestRequest;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.TestService;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

import java.rmi.server.RMIClassLoader;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class TestController {
    private final TestService testService;
      @PostMapping("/tests")
    @ApiMessage("Create new test")
    public ResponseEntity<Test> createNewTest(@RequestBody TestRequest testRequest) throws EntityExistsException{
        return ResponseEntity.status(HttpStatus.CREATED).body(this.testService.createNewTest(testRequest));
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable("id") String id) throws InvalidIDException{
        if (!HandleNumber.isNumberic(id)) {
            throw new InvalidIDException("Invalid Id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.testService.getTestByID(Long.parseLong(id)));
    }
    
    
}
