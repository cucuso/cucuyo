package com.cucuyo.web.exceptions;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
class ErrorResource {

    private final LocalDateTime timestamp;

    private int status;

    private String error;

    private String exception;

    private String message;

    private String path;

    private FieldErrorResource field;

    ErrorResource() {
        timestamp = LocalDateTime.now();
    }
}
