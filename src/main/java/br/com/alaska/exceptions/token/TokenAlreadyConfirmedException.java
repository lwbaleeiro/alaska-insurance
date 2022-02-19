package br.com.alaska.exceptions.token;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class TokenAlreadyConfirmedException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.error.tokenAlreadyConfirmedException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "Token already confirmed!";
    }
}
