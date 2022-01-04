package com.gafahtec.consultorio.exception;

public class ModeloNotFoundException extends RuntimeException{
    public ModeloNotFoundException() {
    }

    public ModeloNotFoundException(String message) {
        super(message);
    }

    public ModeloNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModeloNotFoundException(Throwable cause) {
        super(cause);
    }

    public ModeloNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
