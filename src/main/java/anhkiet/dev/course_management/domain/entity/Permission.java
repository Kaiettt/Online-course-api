package anhkiet.dev.course_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),

    STUDENT_READ("student:read"),
    STUDENT_CREATE("student:create"),
    STUDENT_UPDATE("student:update"),
    STUDENT_DELETE("student:delete"),

    INSTRUCTOR_READ("instructor:read"),
    INSTRUCTOR_CREATE("instructor:create"),
    INSTRUCTOR_UPDATE("instructor:update"),
    INSTRUCTOR_DELETE("instructor:delete");

    @Getter
    private final String permission;
}

