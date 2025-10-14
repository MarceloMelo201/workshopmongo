package com.marcelomelo.workshopmongo.exception;

public class CommentNotFound extends RuntimeException {
    public CommentNotFound() {
        super("Comment not found.");
    }
}
