package edu.setokk.atm.error.exception.user;

import edu.setokk.atm.error.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class EmailExistsException extends BusinessLogicException {
    public EmailExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
