package org.xpdojo.spring.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xpdojo.spring.error.ErrorResponse;

@RestControllerAdvice
public class ValidationControllerAdvice {

    @ExceptionHandler({
            BindException.class,
            MethodArgumentNotValidException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();

        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append(" : ");
        sb.append(fieldError.getDefaultMessage());
        return new ErrorResponse(sb.toString());
    }
}
