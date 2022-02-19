package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsWithEmailException extends BaseException {

    @Override
    public String getCode() {
        return "freyer.bank.error.userAlreadyExistsWithEmailException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public String getMessage() {
        return "User already exists with this email.";
    }
}
