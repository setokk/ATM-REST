package edu.setokk.atm.error.exception.user;

import edu.setokk.atm.error.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends BusinessLogicException {
    public InsufficientBalanceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
