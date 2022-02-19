package br.com.alaska.service.user;

import br.com.alaska.controllers.user.response.UserResponse;
import br.com.alaska.entity.user.User;

import java.util.List;

public interface UserService {

    void createUser(User user);
    UserResponse alterUser(User user);
    UserResponse findUserById(String id);
    List<UserResponse> findAll();
    void confirmation(String token);
    void inactivate(String id);
}
