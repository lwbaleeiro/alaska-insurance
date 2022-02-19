package br.com.alaska.exceptions.email;

import br.com.alaska.config.interceptors.BaseException;
import org.springframework.http.HttpStatus;

public class FailedEmailSenderException extends BaseException {

    @Override
    public String getCode() {
        return "alaska.email.error.failedEmailSenderException";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return "Failed to send email.";
    }
}
