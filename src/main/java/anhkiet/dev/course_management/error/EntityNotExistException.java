package anhkiet.dev.course_management.error;

public class EntityNotExistException extends RuntimeException{
    public EntityNotExistException(String ex){
        super(ex);
    }
}
