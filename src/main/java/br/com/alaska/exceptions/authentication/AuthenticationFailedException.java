package br.com.alaska.exceptions.authentication;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.authentication.error.authenticationFailedException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return "Failed to authenticate";
    }
}
