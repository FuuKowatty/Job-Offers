package pl.bartoszmech.domain.accountidentifier;

import org.junit.jupiter.api.Test;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AccountIdentifierFacadeTest {
    AccountIdentifierFacade configurator = new AccountIdentifierConfiguration().createForTests(new UserValidator(), new AccountIdentifierRepositoryTestImpl());
    @Test void should_find_user_by_username() {
        //given
        String username = "Jacex";
        UserDto expectedUser = UserMapper.mapFromUser(configurator.repository.save(User.builder().username(username).password("123").build()));
        //when
        UserDto user = configurator.findByUsername(username);
        //then
        assertThat(user).isEqualTo(expectedUser);
    }

    @Test void should_throw_exception_if_user_dont_exists() {
        //given
        String nonExistentUsername = "Jacex";
        //when&then
        assertThrows(UserNotFoundException.class, () -> configurator.findByUsername(nonExistentUsername));
    }

    @Test void should_throw_user_already_exists_exception() {
        //given
        String username = "Jacex";
        String password = "QWERY!@345";
        UserDto expectedUser = UserMapper.mapFromUser(configurator.repository.save(User.builder().username(username).password(password).build()));
        //when&then
        assertThrows(UserAlreadyExistsException.class, () -> configurator.register(UserDto.builder().username(username).password(password).build()));
    }

    @Test void should_successfully_register_user() {
        //given
        String username = "Jacex";
        String password = "QWERTYU!@3456";
        //when
        UserDto userDto = configurator.register(UserDto.builder().username(username).password(password).build());
        //then
        assertThat(userDto.username()).isEqualTo(username);
        assertThat(userDto.password()).isEqualTo(password);
    }
}



