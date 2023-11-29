package edu.setokk.atm.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ErrorMessage {
    private String message;
    private int statusCode;
}
