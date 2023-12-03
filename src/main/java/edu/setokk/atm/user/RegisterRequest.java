package edu.setokk.atm.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {
    @NotNull(message = "username field required")
    private String username;

    @NotNull(message = "password field required")
    private String password;

    @NotNull(message = "email field is required")
    private String email;
}
