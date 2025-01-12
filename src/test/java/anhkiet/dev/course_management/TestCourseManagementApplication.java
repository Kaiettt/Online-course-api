package anhkiet.dev.course_management;

import org.springframework.boot.SpringApplication;

public class TestCourseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(CourseManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
