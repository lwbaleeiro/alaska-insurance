package br.com.alaska.config.interceptors;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    private static final String ERROR_TO_RESOLVE_EXCEPTION_HANDLER = "Error to resolve exception handler";
    private static final String SAO_EXCEPTION_HANDLER_ERROR_TO_RESOLVE_EXCEPTION = "sao.exceptionHandler.errorToResolveException";

    @ExceptionHandler({ BaseException.class})
    @ResponseBody
    public ResponseEntity<ExceptionJson> saoException(final BaseException e, final HttpServletResponse response) {

        log.error(e.getMessage(), e);
        final ExceptionJson exceptionJson = new ExceptionJson(e);

        return new ResponseEntity<>(exceptionJson, new HttpHeaders(), e.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionJson validationException(final MethodArgumentNotValidException methodArgumentNotValidException) {

        log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        try {

            final ArrayList<String> errors = new ArrayList<>();
            methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(field -> errors.add(field.getField() + ": " + field.getDefaultMessage()));

            return new ExceptionJson("alaska.error.argumentNotValid", errors.toString());
        } catch (Exception exception) {
            log.error(ERROR_TO_RESOLVE_EXCEPTION_HANDLER, exception);
            return new ExceptionJson(SAO_EXCEPTION_HANDLER_ERROR_TO_RESOLVE_EXCEPTION, exception.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseBody
    public ExceptionJson dateTimeParseException(final DateTimeParseException dateTimeParseException) {

        log.error(dateTimeParseException.getMessage(), dateTimeParseException);
        try {
            var error = "Error parsing value '" + dateTimeParseException.getParsedString() + "'. Expecting date format yyyy-MM-dd";

            return new ExceptionJson("alaska.error.dateTimeInvalidFormat", error);
        } catch (Exception exception) {
            log.error(ERROR_TO_RESOLVE_EXCEPTION_HANDLER, exception);
            return new ExceptionJson("alaska.error.dateTimeInvalidFormat", exception.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public ExceptionJson invalidFormatException(final InvalidFormatException invalidFormatException) {

        log.error(invalidFormatException.getMessage(), invalidFormatException);
        try {

            return new ExceptionJson("alaska.error.invalidFormatException", invalidFormatException.getOriginalMessage());

        } catch (Exception exception) {
            log.error(ERROR_TO_RESOLVE_EXCEPTION_HANDLER, exception);
            return new ExceptionJson("alaska.error.dateInvalidFormat", exception.getMessage());
        }
    }

}
