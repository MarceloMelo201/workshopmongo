package com.marcelomelo.workshopmongo.infra;

import com.marcelomelo.workshopmongo.exception.PostNotFound;
import com.marcelomelo.workshopmongo.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFound.class, PostNotFound.class})
    private ResponseEntity<RestApiError> notFoundHandler(RuntimeException exception){
        RestApiError restApiError = RestApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .timeStamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .errors(List.of(exception.getMessage()))
                .build();

        return new ResponseEntity<>(restApiError, HttpStatus.NOT_FOUND);
    }
}
