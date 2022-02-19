package br.com.alaska.controllers.user;

import br.com.alaska.controllers.user.form.CreateUserForm;
import br.com.alaska.controllers.user.response.UserResponse;
import br.com.alaska.entity.user.User;
import br.com.alaska.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "User API")
@RequestMapping("/v1/user")
public record UserController(UserService usersService,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {

    @Operation(summary = "Create a new user")
    @PostMapping("/create")
    public ResponseEntity<String> create(final @RequestBody @Valid CreateUserForm createUsersForm) {

        log.info(" createUsersForm {}", createUsersForm);

        User user = User
                .builder()
                .cpf(createUsersForm.getCpf())
                .name(createUsersForm.getName())
                .cellphone(createUsersForm.getCellphone())
                .dateOfBirth(createUsersForm.getDateOfBirth())
                .sex(createUsersForm.getSex())
                .email(createUsersForm.getEmail())
                .password(bCryptPasswordEncoder.encode(createUsersForm.getPassword()))
                .usersRole(createUsersForm.getUsersRole())
                .enabled(Boolean.FALSE)
                .build();

        usersService.createUser(user);

        return ResponseEntity.ok("User created successfully! Please confirm your e-mail for activate your account.");
    }

    @Operation(summary = "Confirm the user creation account")
    @GetMapping("/confirmation")
    public ResponseEntity<String> confirmation(final @RequestParam String token) {
        log.info(" confirmation token {} ", token);
        usersService.confirmation(token);

        return ResponseEntity.ok("Token confirmed. The user is now enabled!");
    }

    @Operation(summary = "Alter an existent user")
    @PutMapping("/alter/{id}")
    public UserResponse alter(final @RequestBody @Valid CreateUserForm createUsersForm, final @PathVariable String id) {

        log.info(" createUsersForm {}, id {}", createUsersForm, id);

        User user = User
                .builder()
                .id(Long.valueOf(id))
                .cpf(createUsersForm.getCpf())
                .name(createUsersForm.getName())
                .cellphone(createUsersForm.getCellphone())
                .dateOfBirth(createUsersForm.getDateOfBirth())
                .sex(createUsersForm.getSex())
                .email(createUsersForm.getEmail())
                .password(bCryptPasswordEncoder.encode(createUsersForm.getPassword()))
                .usersRole(createUsersForm.getUsersRole())
                .enabled(createUsersForm.getEnabled())
                .build();

        return usersService.alterUser(user);
    }

    @Operation(summary = "Inactivate an specific user by the id")
    @PutMapping("/inactivate/{id}")
    public ResponseEntity<String> inactivate(final @PathVariable String id) {

        log.info(" inactivate {}", id);

        usersService.inactivate(id);
        return ResponseEntity.ok("User inactivated successfully!");
    }

    @Operation(summary = "Return an specific user by the id")
    @GetMapping("/{id}")
    public UserResponse getUserById(final @PathVariable String id) {

        log.info(" geUserById {}", id);

        return usersService.findUserById(id);
    }

    @Operation(summary = "Return a list of User")
    @GetMapping("/all")
    public List<UserResponse> getListOfUsers() {

        log.info(" getListOfUsers ");

        return usersService.findAll();
    }

}
