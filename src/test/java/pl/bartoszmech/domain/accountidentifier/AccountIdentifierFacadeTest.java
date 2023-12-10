package pl.bartoszmech.domain.accountidentifier;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import pl.bartoszmech.domain.accountidentifier.dto.RegistrationResultDto;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.RegisterRequestDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AccountIdentifierFacadeTest {
    AccountIdentifierFacade loginFacade = new AccountIdentifierFacade(
            new AccountIdentifierRepositoryTestImpl()
    );

    @Test
    public void should_register_user() {
        // given
        UserDto registerUserDto = new UserDto("username", "pass");

        // when
        RegistrationResultDto register = loginFacade.register(registerUserDto);

        // then
        assertAll(
                () -> assertThat(register.created()).isTrue(),
                () -> assertThat(register.username()).isEqualTo("username")
        );
    }

    @Test
    public void should_find_user_by_user_name() {
        // given
        UserDto registerUserDto = new UserDto("username", "pass");
        RegistrationResultDto register = loginFacade.register(registerUserDto);

        // when
        UserDto userByName = loginFacade.findByUsername(register.username());

        // then
        assertThat(userByName).isEqualTo(new UserDto("username", "pass"));
    }

    @Test
    public void should_throw_exception_when_user_not_found() {
        // given
        String username = "someUser";

        // when
        Throwable thrown = catchThrowable(() -> loginFacade.findByUsername(username));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("User with such username does not exist");
    }
}



