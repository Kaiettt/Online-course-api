package anhkiet.dev.course_management.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {
  @Value("${ak.upload-file.base-uri}")
  private String basePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/storage/**").addResourceLocations(basePath);
  }
}
