package br.com.alaska.exceptions.token;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.error.tokenExpiredException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.REQUEST_TIMEOUT;
    }

    @Override
    public String getMessage() {
        return "This token already expires!";
    }
}
