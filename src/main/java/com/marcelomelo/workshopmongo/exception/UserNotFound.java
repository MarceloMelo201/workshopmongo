package com.marcelomelo.workshopmongo.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User not found.");
    }
}
