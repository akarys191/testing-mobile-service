package com.testing.controller;

import com.testing.dto.ResponseTO;
import com.testing.exception.AlreadyBookedException;
import com.testing.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("unused")
@RestControllerAdvice
@Slf4j
public class TestingMobileControllerHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseTO<String> exceptionHandler(Exception ex) {
        return ResponseTO.badError("Sorry Something went wrong in the server");
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseTO<String> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseTO.notFoundClientError(ex.getMessage());

    }

    @ExceptionHandler(value = {AlreadyBookedException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseTO<String> orderExceptionHandler(AlreadyBookedException ex) {
        return ResponseTO.badClientError(ex.getMessage());

    }

}
