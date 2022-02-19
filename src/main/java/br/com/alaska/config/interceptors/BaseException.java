package br.com.alaska.config.interceptors;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    public abstract String getCode();
    public abstract HttpStatus getHttpStatus();
    public abstract String getMessage();

}
