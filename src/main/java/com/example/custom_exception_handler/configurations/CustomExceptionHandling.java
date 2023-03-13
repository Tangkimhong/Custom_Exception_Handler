package com.example.custom_exception_handler.configurations;

import com.example.custom_exception_handler.entities.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandling extends ResponseEntityExceptionHandler {

    /*
     *  Custom exception in Method request
     */
    @Override
    protected ResponseEntity<Object>
    handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                        HttpHeaders headers,
                                        HttpStatusCode status,
                                        WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" Method is not supported for this request. Supported this method are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
                                         LocalDateTime.now(), builder.toString());
        return new ResponseEntity<>(apiError,new HttpHeaders(),apiError.getStatus());
    }


    /*
     *  Custom exception request body
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                LocalDateTime.now(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }


    /*
     * Default exception
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handlerSomeControllerEx(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(),
                LocalDateTime.now(),
                "error occurred");

        return new ResponseEntity<>(apiError,new HttpHeaders(),apiError.getStatus());
    }
}
