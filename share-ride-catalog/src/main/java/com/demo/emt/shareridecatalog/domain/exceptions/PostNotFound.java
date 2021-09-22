package com.demo.emt.shareridecatalog.domain.exceptions;

public class PostNotFound extends RuntimeException{
    public PostNotFound() {
        super("Post not found with this id");
    }
}
