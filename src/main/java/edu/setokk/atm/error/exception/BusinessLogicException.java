package edu.setokk.atm.error.exception;

import org.springframework.http.HttpStatus;

/*
 *  Custom exception class used as a parent for all other business logic exceptions.
 *  We do this so that we don't have to write a custom exception handle method in
 *  GlobalExceptionHandler every time we create a custom exception.
 *
 *  Instead, we use this class to handle any business logic exceptions that may be thrown.
 * */
public class BusinessLogicException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BusinessLogicException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
