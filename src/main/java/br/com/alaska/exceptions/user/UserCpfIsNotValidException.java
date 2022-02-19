package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class UserCpfIsNotValidException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.users.error.usersCpfIsNotValidException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "Cpf is not valid!";
    }
}
