package edu.setokk.atm.security.auth;

import edu.setokk.atm.user.User;
import edu.setokk.atm.user.UserDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

@Component
public class JwtUtil {
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

    public static String generateJWT(User user) {
        Instant currentInstant = Instant.now();
        return Jwts.builder()
                .issuer("eclass")
                .subject(String.valueOf(user.getId()))
                .claim("role", "user")
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("balance", user.getBalance())
                .issuedAt(Date.from(currentInstant))
                .expiration(Date.from(currentInstant.plus(Duration.ofDays(2))))
                .signWith(secretKey)
                .compact();
    }
}
