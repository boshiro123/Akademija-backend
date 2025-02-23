package akademia.demo.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AuthResponseDTO {
  private String userType; // PARENT, TEACHER, ADMIN
  private Long userId;
  private String email;
  private String firstName;
  private String lastName;
  private String password;
}