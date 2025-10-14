package com.marcelomelo.workshopmongo.exception;

public class PostNotFound extends RuntimeException {
    public PostNotFound() {
        super("Post not found.");
    }
}
