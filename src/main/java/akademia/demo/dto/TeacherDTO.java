package akademia.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class TeacherDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private List<LessonDTO> lessons;
}