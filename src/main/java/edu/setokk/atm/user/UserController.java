package edu.setokk.atm.user;

import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final static Key secretKey;
    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(new SecureRandom());
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UserDTO userDTO = userService
                .authenticateUser(username, password)
                .ofDTO();

        // Generate JWT Token
        return ResponseEntity.ok(generateJWT(userDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid
                                          RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();

        UserDTO userDTO = userService
                .registerUser(username, password, email)
                .ofDTO();

        // Generate JWT Token
        return ResponseEntity.ok(generateJWT(userDTO));
    }

    public String generateJWT(UserDTO userDTO) {
        Instant currentInstant = Instant.now();
        return Jwts.builder()
                .issuer("eclass")
                .subject(String.valueOf(userDTO.getId()))
                .claim("role", "user")
                .claim("id", userDTO.getId())
                .claim("username", userDTO.getUsername())
                .claim("email", userDTO.getEmail())
                .claim("balance", userDTO.getBalance())
                .issuedAt(Date.from(currentInstant))
                .expiration(Date.from(currentInstant.plus(Duration.ofDays(2))))
                .signWith(secretKey)
                .compact();
    }
}
