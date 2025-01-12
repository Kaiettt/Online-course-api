package anhkiet.dev.course_management.error;

public class HandleNumber {
    public static boolean isNumberic(String s) {
        if (s == null) {
            return false;
        }
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            // TODO: handle exception
            return false;
        }
    }
}
