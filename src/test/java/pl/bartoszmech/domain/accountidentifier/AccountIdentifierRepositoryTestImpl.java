package pl.bartoszmech.domain.accountidentifier;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountIdentifierRepositoryTestImpl implements AccountIdentifierRepository {
    Map<String, User> database = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        database.put(user.username(),user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User searchingUser = database
                .values()
                .stream()
                .filter(user -> user.username().equals(username))
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(searchingUser);
    }

    @Override
    public User register(User user) {
        database.put(user.username(), user);
        return user;
    }


    @Override
    public boolean isExistsByUsername(String username) {
        return database.containsKey(username);
    }
}
