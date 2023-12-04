package edu.setokk.atm.user;

import edu.setokk.atm.auth.JwtUtils;
import edu.setokk.atm.user.request.LoginRequest;
import edu.setokk.atm.user.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @GetMapping("/deposit")
    public ResponseEntity<?> depositAmount(@RequestParam("amount") BigDecimal amount) {
        User authUser = getAuthenticatedUser();
        if (authUser == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userService.deposit(authUser, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/withdraw")
    public ResponseEntity<?> withdrawAmount(@RequestParam("amount") BigDecimal amount) {
        User authUser = getAuthenticatedUser();
        if (authUser == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userService.withdraw(authUser, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User))
            return null;

        return (User) authentication.getPrincipal();

    }
}
