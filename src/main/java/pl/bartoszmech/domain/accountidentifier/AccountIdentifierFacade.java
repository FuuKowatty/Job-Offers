package pl.bartoszmech.domain.accountidentifier;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import pl.bartoszmech.domain.accountidentifier.dto.RegistrationResultDto;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;

@AllArgsConstructor
public class AccountIdentifierFacade {
    private static final String USERNAME_NOT_EXISTS = "User with such username does not exist";
    AccountIdentifierRepository repository;

    public UserDto findByUsername(String username) {
        return UserMapper.mapFromUser(repository
                .findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException(USERNAME_NOT_EXISTS)));
    }

    public RegistrationResultDto register(UserDto registerRequestDto) {
        User user = repository.save(UserMapper.mapToUser(registerRequestDto));
        return new RegistrationResultDto(user.id(), true, user.username());
    }

}
