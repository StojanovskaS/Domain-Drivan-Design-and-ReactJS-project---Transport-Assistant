package com.demo.emt.shareridecatalog.domain.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException() {
        super("You need to fill all required boxes!!!");
    }
}
