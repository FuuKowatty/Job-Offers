package pl.bartoszmech.domain.accountidentifier;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import pl.bartoszmech.domain.accountidentifier.dto.RegistrationResultDto;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.TokenRequestDto;

@AllArgsConstructor
public class AccountIdentifierFacade {
    public static final String USER_NOT_EXISTS = "User with such username does not exist";
    public static final String USERNAME_TAKEN = "Provided username is already used by other user";
    AccountIdentifierRepository repository;

    public UserDto findByUsername(String username) {
        return UserMapper.mapFromUser(repository
                .findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_EXISTS)));
    }

    public RegistrationResultDto register(UserDto userDto) {
        User user = repository.save(UserMapper.mapToUser(userDto));
        return new RegistrationResultDto(user.id(), true, user.username());
    }
}
