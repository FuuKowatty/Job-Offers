package pl.bartoszmech.domain.accountidentifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccountIdentifierConfiguration {
    AccountIdentifierFacade createForTests(AccountIdentifierRepository repository) {
        return new AccountIdentifierFacade(repository);
    }

    @Bean
    AccountIdentifierFacade createForApp(AccountIdentifierRepository repository) {
        return new AccountIdentifierFacade(repository);
    }
}
