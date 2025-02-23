package akademia.demo.service.impl;

import akademia.demo.dto.AuthRequestDTO;
import akademia.demo.dto.AuthResponseDTO;
import akademia.demo.model.Parent;
import akademia.demo.model.Teacher;
import akademia.demo.repository.ParentRepository;
import akademia.demo.repository.TeacherRepository;
import akademia.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final ParentRepository parentRepository;
  private final TeacherRepository teacherRepository;

  @Override
  public AuthResponseDTO authenticate(AuthRequestDTO request) {
    // Сначала проверяем среди родителей
    Parent parent = parentRepository.findByEmail(request.getEmail())
        .filter(p -> p.getPassword().equals(request.getPassword()))
        .orElse(null);

    if (parent != null) {
      return AuthResponseDTO.builder()
          .userType("PARENT")
          .userId(parent.getId())
          .email(parent.getEmail())
          .firstName(parent.getFirstName())
          .lastName(parent.getLastName())
          .password(parent.getPassword())
          .build();
    }

    // Затем среди учителей
    Teacher teacher = teacherRepository.findByEmail(request.getEmail())
        .filter(t -> t.getPassword().equals(request.getPassword()))
        .orElse(null);

    if (teacher != null) {
      return AuthResponseDTO.builder()
          .userType("TEACHER")
          .userId(teacher.getId())
          .email(teacher.getEmail())
          .firstName(teacher.getFirstName())
          .lastName(teacher.getLastName())
          .password(teacher.getPassword())
          .build();
    }

    if (request.getEmail().equals("admin@admin.com") && request.getPassword().equals("admin")) {
      return AuthResponseDTO.builder()
          .userType("ADMIN")
          .userId(1L)
          .email("admin@admin.com")
          .firstName("Admin")
          .lastName("Admin")
          .password("admin")
          .build();
    }

    // Если пользователь не найден
    throw new RuntimeException("Invalid credentials");
  }
}
