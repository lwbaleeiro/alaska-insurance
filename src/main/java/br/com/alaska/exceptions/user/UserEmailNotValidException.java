package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class UserEmailNotValidException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.bank.error.usersEmailNotValidException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "The given e-mail is not valid!";
    }
}
