package br.com.alaska.controllers.auth.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AuthenticationForm {

    @NotEmpty(message = "e-mail cannot be empty!")
    private String email;
    @NotEmpty(message = "password cannot be empty!")
    private String password;

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
