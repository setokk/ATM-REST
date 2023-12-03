package edu.setokk.atm.user;

import edu.setokk.atm.error.exception.user.InvalidCredentialsException;
import edu.setokk.atm.error.exception.user.UserNotFoundException;
import edu.setokk.atm.error.exception.user.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticateUser(String username, String password)
            throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User with username '" + username + "' not found.")
                );

        String actualHashedPassword = user.getPassword();
        boolean isValidCredentials = passwordEncoder.matches(password, actualHashedPassword);
        if (!isValidCredentials)
            throw new InvalidCredentialsException("Invalid password");

        return user;
    }

    public User registerUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setBalance(BigDecimal.valueOf(0));

        // Check if user with same username exists
        ExampleMatcher usernameMatcher = ExampleMatcher.matchingAny()
                .withMatcher("username", match -> match.exact())
                .withIgnorePaths("password", "email", "balance", "id");

        boolean usernameExists = userRepository.exists(Example.of(user, usernameMatcher));
        if (usernameExists)
            throw new UsernameExistsException("User with username: " + username + " exists");

        return userRepository.save(user);
    }
}
