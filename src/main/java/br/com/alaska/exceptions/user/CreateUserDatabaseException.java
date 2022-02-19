package br.com.alaska.exceptions.user;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class CreateUserDatabaseException extends BaseException {


    @Override
    public String getCode() {
        return "alaska.users.database.error.createUsersDatabaseException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return "Error to create User.";
    }
}
