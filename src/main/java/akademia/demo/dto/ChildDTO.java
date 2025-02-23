package akademia.demo.dto;

import lombok.Data;

@Data
public class ChildDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private int age;
  private Long parentId;
}