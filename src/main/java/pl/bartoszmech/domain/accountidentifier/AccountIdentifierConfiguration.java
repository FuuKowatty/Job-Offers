package pl.bartoszmech.domain.accountidentifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class AccountIdentifierConfiguration {
    @Bean
    AccountIdentifierFacade createForApp(AccountIdentifierRepository repository) {
        return new AccountIdentifierFacade(repository);
    }
}
