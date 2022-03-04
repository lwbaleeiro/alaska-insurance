package br.com.alaska.service.user;

import br.com.alaska.controllers.user.form.CreateUserForm;
import br.com.alaska.controllers.user.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserForm user);
    UserResponse alterUser(CreateUserForm createUserForm);
    UserResponse findUserById(String id);
    List<UserResponse> findAll();
    void confirmation(String token);
    void inactivate(String id);
}
