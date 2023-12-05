package pl.bartoszmech.domain.accountidentifier;

import java.util.Optional;

interface AccountIdentifierRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
    User register(User user);
    boolean isExistsByUsername(String username);
}
