package br.com.alaska.exceptions.token;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class TokenDoNotExistsException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.database.error.tokenDoNotExistsException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "Token not found in database!";
    }
}
