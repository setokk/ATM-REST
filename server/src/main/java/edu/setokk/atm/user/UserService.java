package edu.setokk.atm.user;

import edu.setokk.atm.error.exception.user.*;
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

    public User loginUser(String username, String password)
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
        user.setBalance(BigDecimal.valueOf(0.0));

        // Check if same username exists
        ExampleMatcher usernameMatcher = ExampleMatcher.matchingAny()
                .withMatcher("username", match -> match.exact())
                .withIgnorePaths("password", "email", "balance", "id");

        boolean usernameExists = userRepository.exists(Example.of(user, usernameMatcher));
        if (usernameExists)
            throw new UsernameExistsException("User with username: " + username + " exists");

        // Check if same email exists
        ExampleMatcher emailMatcher = ExampleMatcher.matchingAny()
                .withMatcher("email", match -> match.exact())
                .withIgnorePaths("username", "password", "balance", "id");

        boolean emailExists = userRepository.exists(Example.of(user, emailMatcher));
        if (emailExists)
            throw new EmailExistsException("Email is taken.");

        return userRepository.save(user);
    }

    public void deposit(User user, BigDecimal amount) {
        long userId = user.getId();

        boolean userExists = userRepository.existsById(userId);
        if (!userExists)
            throw new UserNotFoundException("User with id: " + userId + " not found");

        userRepository.depositAmount(userId, amount);
    }

    public void withdraw(User user, BigDecimal amount) {
        long userId = user.getId();

        boolean userExists = userRepository.existsById(userId);
        if (!userExists)
            throw new UserNotFoundException("User with id: " + userId + " not found");

        boolean withdrawSuccess = userRepository.withdrawAmount(userId, amount) > 0;
        if (!withdrawSuccess)
            throw new InsufficientBalanceException("Insufficient balance for withdraw amount=" + amount);
    }

}
