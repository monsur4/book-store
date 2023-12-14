package com.mon.bookstore.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class BookExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex){
        log.debug("handling BookNotFoundException");
        HttpStatus status = HttpStatus.NOT_FOUND;
        HttpError httpError = HttpError.builder().message(ExceptionHelper.getRootCause(ex).getMessage())
                .status(status)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @ExceptionHandler(BookServiceException.class)
    public ResponseEntity<Object> handleBookServiceException(BookServiceException ex){
        log.debug("handling BookServiceException");
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpError httpError = HttpError.builder().message(ExceptionHelper.getRootCause(ex).getMessage())
                .status(status)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @Data
    @Builder
    public static class HttpError{
        private String message;
        private HttpStatus status;
    }
}
