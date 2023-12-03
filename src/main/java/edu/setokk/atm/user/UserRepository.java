package edu.setokk.atm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.id,u.balance,u.username,u.password,u.email FROM bank_user u WHERE u.username=:username",
    nativeQuery = true)
    Optional<User> findUserByUsername(@Param("username") String username);
}
