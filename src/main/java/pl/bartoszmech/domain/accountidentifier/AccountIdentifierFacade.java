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

    public UserDto register(UserDto userDto) {
        //...validation in the future!
        if(repository.isExistsByUsername(userDto.username())) {
            throw new UserAlreadyExistsException("Provided username is already used by other user");
        }
        User user = repository.register(UserMapper.mapToUser(userDto));
        return UserMapper.mapFromUser(user);
    }
}
