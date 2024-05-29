package com.yohann.exceptions;

public class MultipleTreasureMapSizeException extends Exception {
    public MultipleTreasureMapSizeException() {

    }

    public MultipleTreasureMapSizeException(String errorMessage) {
        super(errorMessage);
    }

    public MultipleTreasureMapSizeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
