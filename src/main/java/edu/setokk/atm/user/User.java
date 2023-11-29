package edu.setokk.atm.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
    name = "bank_user",
    uniqueConstraints = {
        @UniqueConstraint(name = "bank_user_username_unique", columnNames = "username")
    }
)
public class User {
    @Id
    @SequenceGenerator(
            name = "bankUserSeqGen",
            sequenceName = "bank_user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bankUserSeqGen"
    )
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
}
