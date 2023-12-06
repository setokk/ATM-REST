package edu.setokk.atm.user;

import java.math.BigDecimal;

public record UserDTO(long id,
                      BigDecimal balance,
                      String username,
                      String email) {}