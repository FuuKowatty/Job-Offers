package pl.bartoszmech.domain.accountidentifier;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountIdentifierRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}