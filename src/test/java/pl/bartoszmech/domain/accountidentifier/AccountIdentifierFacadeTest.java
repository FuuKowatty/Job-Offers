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
}



