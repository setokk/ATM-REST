package edu.setokk.atm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT id,balance,username,password,email FROM User WHERE username=:username")
    Optional<User> findUserByUsername(@Param("username") String username);
}
