package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundByIdException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.error.userNotFoundByIdException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "User not found by this given id.";
    }
}
