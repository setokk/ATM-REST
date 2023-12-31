package edu.setokk.atm.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class RegisterRequest {
    @NotNull(message = "username field required")
    private String username;

    @NotNull(message = "password field required")
    private String password;

    @NotNull(message = "email field is required")
    @Email(message = "email field is invalid")
    private String email;
}
