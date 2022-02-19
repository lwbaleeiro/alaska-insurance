package br.com.alaska.exceptions.token;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class CreateTokenDatabaseException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.database.error.createTokenDatabaseException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return "Error to save token into database!";
    }
}
