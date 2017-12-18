package com.cucuyo.web.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@RequiredArgsConstructor
class FieldErrorResource {

    private final FieldError fieldError;

    public String getCode() {
        return fieldError.getCode();
    }

    public String getField() {
        return fieldError.getField();
    }

    public Object getRejectedValue() {
        return fieldError.getRejectedValue();
    }
}
