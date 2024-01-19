package br.com.alaska.controllers.user.form;

import br.com.alaska.entity.user.Sex;
import br.com.alaska.entity.user.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Builder
public record CreateUserForm(@NotEmpty(message = "name cannot be empty!")
                             String name,
                             @NotEmpty(message = "cpf cannot be empty!")
                             String cpf,
                             @JsonFormat(shape = OBJECT)
                             Sex sex,
                             @JsonDeserialize(using = LocalDateDeserializer.class)
                             @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
                             LocalDate dateOfBirth,
                             @NotEmpty(message = "email cannot be empty!")
                             String email,
                             String cellphone,
                             @NotEmpty(message = "password cannot be empty!")
                             String password,
                             UserRole usersRole,
                             Boolean enabled) {

}
