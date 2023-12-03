package edu.setokk.atm.user;

import edu.setokk.atm.error.exception.user.InvalidCredentialsException;
import edu.setokk.atm.error.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserDTO authenticateUser(String username, String password)
            throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User with username '" + username + "' not found.")
                );

        String actualHashedPassword = user.getPassword();
        boolean isValidCredentials = passwordEncoder.matches(password, actualHashedPassword);
        if (!isValidCredentials)
            throw new InvalidCredentialsException("Invalid password");

        return user.ofDTO();
    }

    public UserDTO registerUser(String username,
                                String password,
                                String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setBalance(BigDecimal.valueOf(0));

        User savedUser = userRepository.save(user);
        return savedUser.ofDTO();
    }
}
