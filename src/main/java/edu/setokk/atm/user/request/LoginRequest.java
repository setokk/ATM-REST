package edu.setokk.atm.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    @NotNull(message = "username field is required")
    private String username;

    @NotNull(message = "password field is required")
    private String password;
}
