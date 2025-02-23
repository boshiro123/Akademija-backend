package akademia.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LessonDTO {
  private Long id;
  private String subject;
  private LocalDateTime startTime;
  private int durationMinutes;
  private Long teacherId;
  private List<ChildDTO> children;
}