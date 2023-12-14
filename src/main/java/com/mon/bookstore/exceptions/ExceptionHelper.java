package com.mon.bookstore.exceptions;

public class ExceptionHelper {

    public static Throwable getRootCause(Throwable ex){
        Throwable cause = ex.getCause();
        if(cause != null){
            return getRootCause(cause);
        }
        return ex;
    }
}
