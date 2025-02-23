package akademia.demo.controller;

import akademia.demo.dto.AuthRequestDTO;
import akademia.demo.dto.AuthResponseDTO;
import akademia.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }
}