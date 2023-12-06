package edu.setokk.atm.error;

import edu.setokk.atm.error.exception.BusinessLogicException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Used for user input validation (from evildoers).<br>
     * Handles "MethodArgumentNotValidException" and returns appropriate messages in an errors array.
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ConstraintViolationException and returns appropriate messages in an errors array.
     **/
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errors = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BusinessLogicException.<br>
     * <b>Every</b> custom exception must extend BusinessLogicException<br>
     * in order for it to be caught by this method.<br>
     * This way, we have a generic exception that handles any type of custom exceptions.
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorMessage> handleBusinessLogicException(BusinessLogicException e) {
        ErrorMessage error = new ErrorMessage(e.getMessage(), e.getHttpStatus().value());
        return new ResponseEntity<>(error, e.getHttpStatus());
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
