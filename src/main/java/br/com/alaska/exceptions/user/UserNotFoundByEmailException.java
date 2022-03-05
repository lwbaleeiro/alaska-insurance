package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundByEmailException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.error.userNotFoundByEmailException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "User not found by this given email.";
    }
}
