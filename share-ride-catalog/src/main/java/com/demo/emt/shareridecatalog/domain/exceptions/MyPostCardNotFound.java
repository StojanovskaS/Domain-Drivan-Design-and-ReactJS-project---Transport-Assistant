package com.demo.emt.shareridecatalog.domain.exceptions;

public class MyPostCardNotFound  extends RuntimeException{

    public MyPostCardNotFound(String username) {
        super(String.format("User with this username : %s didn't have card for posts! ",username));
    }
}