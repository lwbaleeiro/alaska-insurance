package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsWithCpfException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.error.usersAlreadyExistsWithCpfException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public String getMessage() {
        return "User already exists with this cpf.";
    }
}
