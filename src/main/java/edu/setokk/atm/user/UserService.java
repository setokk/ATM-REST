package edu.setokk.atm.user;

import edu.setokk.atm.error.exception.user.InvalidCredentialsException;
import edu.setokk.atm.error.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        // Prepare user DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setBalance(user.getBalance());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }
}
