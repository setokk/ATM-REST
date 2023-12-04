package edu.setokk.atm.user;

import edu.setokk.atm.config.auth.JwtUtils;
import edu.setokk.atm.user.request.LoginRequest;
import edu.setokk.atm.user.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userService.loginUser(username, password);

        // Generate JWT Token
        return ResponseEntity.ok(JwtUtils.generateJWT(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid
                                          RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();

        User user = userService.registerUser(username, password, email);

        // Generate JWT Token
        return ResponseEntity.ok(JwtUtils.generateJWT(user));
    }
}
