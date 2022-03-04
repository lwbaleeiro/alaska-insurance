package br.com.alaska.controllers.auth.response;

public record AuthenticationResponse(String token,
                                     String type,
                                     String role) {
}
