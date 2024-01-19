package br.com.alaska.controllers.auth;

import br.com.alaska.controllers.auth.form.AuthenticationForm;
import br.com.alaska.controllers.auth.response.AuthenticationResponse;
import br.com.alaska.exceptions.authentication.AuthenticationFailedException;
import br.com.alaska.service.token.TokenService;
import br.com.alaska.service.token.UserAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Authentication API")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserAuthenticationService userAuthenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, UserAuthenticationService userAuthenticationService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping
    @Operation(summary = "Api for authenticate user.")
    public AuthenticationResponse authentication(final @RequestBody @Valid AuthenticationForm authenticationForm) {

        String username = authenticationForm.email();
        String password = authenticationForm.password();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails loadedUser = userAuthenticationService.loadUserByUsername(username);
            String generatedToken = tokenService.generateToken(loadedUser);
            String role = tokenService.getRole(loadedUser);

            return new AuthenticationResponse(generatedToken, "Bearer", role);

        } catch (AuthenticationException e) {
            log.error("Error to authenticate: " + e.getMessage());
            throw new AuthenticationFailedException();
        }
    }
}
