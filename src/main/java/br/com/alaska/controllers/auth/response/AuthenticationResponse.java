package br.com.alaska.controllers.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String type;
    private String role;

}
