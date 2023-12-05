package pl.bartoszmech.domain.accountidentifier;

import lombok.AllArgsConstructor;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;

@AllArgsConstructor
public class AccountIdentifierFacade {
    UserValidator validator;
    AccountIdentifierRepository repository;

    public UserDto findByUsername(String username) {
        User user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with such username does not exist"));
        return UserMapper.mapFromUser(user);
    }
}
