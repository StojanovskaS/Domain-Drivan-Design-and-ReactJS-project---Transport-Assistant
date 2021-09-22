package com.demo.emt.shareridecatalog.domain.exceptions;

public class PasswordsdoNotMatchException extends RuntimeException{
    public PasswordsdoNotMatchException() {
        super("Password and repeatedpassword don't mach!");
    }
}
