package com.testing.exception;

import lombok.Getter;

@Getter
public class AlreadyBookedException extends RuntimeException {
    public AlreadyBookedException(final String message) {
        super(message);
    }
}