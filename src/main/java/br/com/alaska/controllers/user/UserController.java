package br.com.alaska.controllers.user;

import br.com.alaska.controllers.user.form.CreateUserForm;
import br.com.alaska.controllers.user.response.UserResponse;
import br.com.alaska.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "User API")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService usersService;

    public UserController(UserService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(final @RequestBody @Valid CreateUserForm createUserForm) {

        log.info(" createUsersForm {}", createUserForm);

        return ResponseEntity.ok(usersService.createUser(createUserForm));
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

        return usersService.alterUser(createUsersForm);
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
