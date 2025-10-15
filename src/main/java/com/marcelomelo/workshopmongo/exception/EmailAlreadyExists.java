package com.marcelomelo.workshopmongo.exception;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists() {
        super("Email already exists in database.");
    }
}
