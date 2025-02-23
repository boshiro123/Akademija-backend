package akademia.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ParentDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<ChildDTO> children;
}