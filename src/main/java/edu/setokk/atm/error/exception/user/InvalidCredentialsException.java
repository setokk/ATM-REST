package edu.setokk.atm.error.exception.user;

import edu.setokk.atm.error.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessLogicException {
    public InvalidCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
