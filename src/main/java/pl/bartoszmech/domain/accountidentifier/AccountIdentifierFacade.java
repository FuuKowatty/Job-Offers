package pl.bartoszmech.domain.accountidentifier;

import lombok.AllArgsConstructor;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;

@AllArgsConstructor
public class AccountIdentifierFacade {
    public static final String USER_NOT_EXISTS = "User with such username does not exist";
    public static final String USERNAME_TAKEN = "Provided username is already used by other user";
    UserValidator validator;
    AccountIdentifierRepository repository;

    public UserDto findByUsername(String username) {
        User user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_EXISTS));
        return UserMapper.mapFromUser(user);
    }

    public UserDto register(UserDto userDto) {
        //...validation in the future!
        if(repository.isExistsByUsername(userDto.username())) {
            throw new UserAlreadyExistsException(USERNAME_TAKEN);
        }
        User user = repository.register(UserMapper.mapToUser(userDto));
        return UserMapper.mapFromUser(user);
    }
}
