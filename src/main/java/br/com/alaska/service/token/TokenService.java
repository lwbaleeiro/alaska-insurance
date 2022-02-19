package br.com.alaska.service.token;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateToken(Authentication authentication);
    String getRoles(Authentication authentication);
    Long getIdUsuario(String token);
    String resolveToken(String token);
}
