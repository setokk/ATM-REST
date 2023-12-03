package edu.setokk.atm.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.Base64;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final static Key secretKey;
    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
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

        UserDTO userDTO = userService.authenticateUser(username, password);

        // Generate JWT Token
        Instant currentInstant = Instant.now();
        String jwt = Jwts.builder()
                .issuer("eclass")
                .subject(String.valueOf(userDTO.getId()))
                .claim("role", "user")
                .issuedAt(Date.from(currentInstant))
                .expiration(Date.from(currentInstant.plus(Duration.ofDays(2))))
                .signWith(secretKey)
                .compact();

        return ResponseEntity.ok(jwt);
    }
}
