package anhkiet.dev.course_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anhkiet.dev.course_management.domain.entity.User;
import anhkiet.dev.course_management.domain.request.UserRequest;

import anhkiet.dev.course_management.config.ApiMessage;
import anhkiet.dev.course_management.error.EmailAlreadyExistsException;
import anhkiet.dev.course_management.error.HandleNumber;
import anhkiet.dev.course_management.error.InvalidIDException;
import anhkiet.dev.course_management.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiMessage("Create new User")
    public ResponseEntity<User> createNewUser(@RequestBody UserRequest requestUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createNewUser(requestUser));
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Fetching User")
    public ResponseEntity<User> getUser(@PathVariable("id") String id)
            throws InvalidIDException{
        if (!HandleNumber.isNumberic(id)) {
            throw new InvalidIDException("Invalid Id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(Long.parseLong(id)));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete User")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id)
            throws InvalidIDException{
        if (!HandleNumber.isNumberic(id)) {
            throw new InvalidIDException("Invalid Id");
        }
        this.userService.deleteUserById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
