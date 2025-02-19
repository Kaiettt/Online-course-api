package anhkiet.dev.course_management.error;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String ex){
        super(ex);
    }
}
