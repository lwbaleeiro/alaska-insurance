package br.com.alaska.controllers.user.form;

import br.com.alaska.entity.user.Sex;
import br.com.alaska.entity.user.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@ToString
public class CreateUserForm {

    @NotEmpty(message = "name cannot be empty!")
    private String name;
    @NotEmpty(message = "cpf cannot be empty!")
    private String cpf;
    private Sex sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "email cannot be empty!")
    private String email;
    private String cellphone;
    @NotEmpty(message = "password cannot be empty!")
    private String password;
    private UserRole usersRole;
    private Boolean enabled;

}
