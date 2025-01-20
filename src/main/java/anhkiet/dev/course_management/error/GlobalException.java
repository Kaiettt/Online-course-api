package anhkiet.dev.course_management.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import anhkiet.dev.course_management.domain.responce.RestResponce;
import jakarta.persistence.EntityExistsException;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(
      value = {
        InvalidIDException.class,
        EntityExistsException.class,
        ResourceExistException.class,
        VerificationException.class
      })
  public ResponseEntity<RestResponce<Object>> badRequestException(Exception ex) {
    RestResponce<Object> res = new RestResponce<Object>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError(ex.getMessage());
    res.setMessage("Exception occurs...");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
@ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponce<Object>> validationError(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    final List<FieldError> fieldErrors = result.getFieldErrors();

    RestResponce<Object> res = new RestResponce<Object>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError("Bad request");

    List<String> errors =
        fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList());
    res.setMessage(errors.size() > 1 ? errors : errors.get(0));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
  @ExceptionHandler(
      value = {
        UsernameNotFoundException.class,
        BadCredentialsException.class
      })
  public ResponseEntity<RestResponce<Object>> handleAuthenticationException(Exception ex) {
    RestResponce<Object> res = new RestResponce<Object>();
    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    res.setError(ex.getMessage());
    res.setMessage("Exception occurs...");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }
}
