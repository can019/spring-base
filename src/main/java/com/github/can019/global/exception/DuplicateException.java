package com.github.can019.global.exception;

public class DuplicateException extends RuntimeException{
    public static String DEFAULT_MESSAGE = "Duplicated exception.";

    public DuplicateException() {
        this("", null);
    }

    public DuplicateException(String message){
        this(message, null);
    }

    public DuplicateException(Throwable cause) {
        this("", cause);
    }

    public DuplicateException(String message, Throwable cause){
        super(DEFAULT_MESSAGE + " " + message, cause);
    }
}
