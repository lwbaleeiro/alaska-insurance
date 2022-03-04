package br.com.alaska.converters.user;

import br.com.alaska.controllers.user.form.CreateUserForm;
import br.com.alaska.entity.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record UserConverter(BCryptPasswordEncoder bCryptPasswordEncoder) {

    public User convert(CreateUserForm createUserForm) {

        return User
                .builder()
                .cpf(createUserForm.cpf())
                .name(createUserForm.name())
                .cellphone(createUserForm.cellphone())
                .dateOfBirth(createUserForm.dateOfBirth())
                .sex(createUserForm.sex())
                .email(createUserForm.email())
                .password(bCryptPasswordEncoder.encode(createUserForm.password()))
                .usersRole(createUserForm.usersRole())
                .enabled(Boolean.FALSE)
                .build();
    }
}
