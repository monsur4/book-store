package com.mon.bookstore.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class BookExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex) {
        log.warn("handling BookNotFoundException");
        HttpStatus status = HttpStatus.NOT_FOUND;
        HttpError httpError = HttpError.builder().message(ExceptionHelper.getRootCause(ex).getMessage())
                .status(status)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @ExceptionHandler(BookServiceException.class)
    public ResponseEntity<Object> handleBookServiceException(BookServiceException ex) {
        log.warn("handling BookServiceException : {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpError httpError = HttpError.builder().message(ExceptionHelper.getRootCause(ex).getMessage())
                .status(status)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("handling ConstraintViolationException : {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpError httpError = HttpError.builder().message(ExceptionHelper.getRootCause(ex).getMessage())
                .status(status)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("handling MethodArgumentNotValidException: {}", ex.getMessage(), ex);

        Map<String, Object> jsonErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            String defaultMessage = err.getDefaultMessage();
            Object rejectedValue = ((FieldError) err).getRejectedValue();

            jsonErrors.put(field, defaultMessage + " => [rejected value: " + rejectedValue + "]");
        });

        List<HttpErrorItem> httpErrorItems = HttpErrorItem.fromMap(jsonErrors);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpError httpError = HttpError.builder().message("Invalid request payload")
                .status(status)
                .errors(httpErrorItems)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        log.warn("handling GenericException: {}", ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpError httpError = HttpError.builder().message(ExceptionHelper.getRootCause(ex).getMessage())
                .status(status)
                .build();

        return new ResponseEntity<>(httpError, status);
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HttpError {
        private String message;
        private HttpStatus status;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<HttpErrorItem> errors;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HttpErrorItem {
        private String message;

        private String field;

        public static List<HttpErrorItem> fromMap(Map<String, Object> errorMap) {
            List<HttpErrorItem> errorList = new ArrayList<>();
            errorMap.forEach((field, message) -> errorList.add(
                    HttpErrorItem.builder()
                            .message((String) message)
                            .field(field)
                            .build()
            ));
            return errorList;
        }
    }
}
