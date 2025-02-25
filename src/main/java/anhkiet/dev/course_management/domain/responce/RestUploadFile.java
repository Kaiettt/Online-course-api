package anhkiet.dev.course_management.domain.responce;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RestUploadFile {
    
  private String uri;
  private Instant createdAt;
  private String fileName;
  private String folder;
  private String name;
}
