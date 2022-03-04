package br.com.alaska.controllers.auth;

import br.com.alaska.controllers.auth.form.AuthenticationForm;
import br.com.alaska.controllers.auth.response.AuthenticationResponse;
import br.com.alaska.entity.user.User;
import br.com.alaska.exceptions.authentication.AuthenticationFailedException;
import br.com.alaska.repository.user.UserRepository;
import br.com.alaska.service.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "Authentication API")
@RequestMapping("/api/v1/auth")
public record AuthenticationController(AuthenticationManager authenticationManager,
                                       TokenService tokenService,
                                       UserRepository userRepository) {

    @PostMapping
    @Operation(summary = "Api for authenticate user.")
    public AuthenticationResponse authentication(final @RequestBody @Valid AuthenticationForm authenticationForm) {

        UsernamePasswordAuthenticationToken loginDatas = authenticationForm.converter();

        try {
            log.info("Try to authenticate");
            Authentication authentication = authenticationManager.authenticate(loginDatas);
            log.info("Success to authenticate with form");
            String token = tokenService.generateToken(authentication);
            String role = tokenService.getRoles(authentication);

            return new AuthenticationResponse(token, "Bearer", role);
        } catch (AuthenticationException e) {
            log.error("Error to authenticate: " + e.getMessage());
            throw new AuthenticationFailedException();
        }
    }

    @GetMapping("/token")
    @Operation(summary = "Api for validation Token")
            public ResponseEntity<User> returnUserByToken(@RequestHeader("Authorization") String token) {
        try {
            log.info("Try to authenticate");
            String bearerToken = tokenService.resolveToken(token);
            Long idUser = tokenService.getIdUsuario(bearerToken);
            Optional<User> optionalUsers = userRepository.findById(idUser);

            return optionalUsers.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (AuthenticationException e) {
            log.error("Error to authenticate: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
