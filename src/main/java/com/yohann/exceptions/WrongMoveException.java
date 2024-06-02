package com.yohann.exceptions;

public class WrongMoveException extends RuntimeException {
    public WrongMoveException() {

    }

    public WrongMoveException(String errorMessage) {
        super(errorMessage);
    }

    public WrongMoveException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
