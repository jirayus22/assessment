package com.kbtg.bootcamp.posttest.exception;

public class DuplicateUsernameException extends RuntimeException{
    public DuplicateUsernameException(String message){
        super(message);
    }
}
