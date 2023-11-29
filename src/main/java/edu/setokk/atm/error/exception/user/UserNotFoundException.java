package edu.setokk.atm.error.exception.user;

import edu.setokk.atm.error.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessLogicException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
