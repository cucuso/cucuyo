package com.cucuyo.web.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class RestErrorHandler {

    private static ResponseEntity<ErrorResource> createResponse(ErrorResponseAttributes attributes) {
        val error = new ErrorResource();
        error.setException(attributes.getException());
        error.setStatus(attributes.getStatus());
        error.setError(attributes.getReason());
        error.setMessage(attributes.getMessage());
        error.setPath(attributes.getPath());
        return new ResponseEntity<>(error, attributes.status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResource> handleException(Exception ex, HttpServletRequest request) {
        val attributes = new ErrorResponseAttributes(ex);
        attributes.request = request;
        attributes.status = INTERNAL_SERVER_ERROR;
        attributes.message = ex.getMessage();
        return createResponse(attributes);
    }

    @RequiredArgsConstructor
    static class ErrorResponseAttributes {

        private String message;

        private HttpStatus status;

        private HttpServletRequest request;

        private Exception exception;

        private FieldError fieldError;

        ErrorResponseAttributes(Exception exception) {
            this.exception = exception;
            if (exception instanceof MethodArgumentNotValidException) {
                val error = (MethodArgumentNotValidException) exception;
                fieldError = error.getBindingResult().getFieldError();
            }
        }

        String getException() {
            return exception.getClass().getSimpleName();
        }

        int getStatus() {
            return status.value();
        }

        String getReason() {
            return status.getReasonPhrase();
        }

        String getMessage() {
            return fieldError == null ? message : fieldError.getDefaultMessage();
        }

        String getPath() {
            return request.getRequestURI();
        }
    }
}

