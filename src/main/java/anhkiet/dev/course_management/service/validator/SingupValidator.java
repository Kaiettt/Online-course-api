package anhkiet.dev.course_management.service.validator;

import anhkiet.dev.course_management.domain.request.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SingupValidator implements ConstraintValidator<Signup, SignupRequest> {
  @Override
  public void initialize(Signup constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }
    @Override
    public boolean isValid(SignupRequest request, ConstraintValidatorContext context) {
        boolean isValid = true;
        if(!request.getPassword().equals(request.getConfirmPassword())){
          context
          .buildConstraintViolationWithTemplate("Password not match")
          .addPropertyNode("confirmPassword")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
          isValid = false;
        }
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (request.getPassword() == null || request.getPassword().isEmpty() || !request.getPassword().matches(passwordPattern)) {
          context
          .buildConstraintViolationWithTemplate("Password is weak")
          .addPropertyNode("password")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
            return false;
        }
        return isValid;
    }
}
