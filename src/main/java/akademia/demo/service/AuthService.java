package akademia.demo.service;

import akademia.demo.dto.AuthRequestDTO;
import akademia.demo.dto.AuthResponseDTO;

public interface AuthService {
  AuthResponseDTO authenticate(AuthRequestDTO request);
}