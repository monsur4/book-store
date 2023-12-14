package com.mon.bookstore.exceptions;

public class BookServiceException extends RuntimeException{

    public BookServiceException(String message) {
        super(message);
    }
}
