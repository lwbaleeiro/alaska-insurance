package br.com.alaska.controllers.auth.form;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record AuthenticationForm(@NotEmpty(message = "e-mail cannot be empty!")
                                 String email,
                                 @NotEmpty(message = "password cannot be empty!")
                                 String password) {

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
