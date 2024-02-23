package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handelNotFoundException(NotFoundException notFoundException){

        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                notFoundException.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateUsernameException.class})
    public ResponseEntity<Object> handelDuplicateEmailException(DuplicateUsernameException duplicateUsernameException){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                duplicateUsernameException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.BAD_REQUEST);
    }
}
