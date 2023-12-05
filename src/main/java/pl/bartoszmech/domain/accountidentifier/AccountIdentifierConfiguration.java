package pl.bartoszmech.domain.accountidentifier;

class AccountIdentifierConfiguration {
    AccountIdentifierFacade createForTests(UserValidator validator, AccountIdentifierRepository repository) {
        return new AccountIdentifierFacade(validator, repository);
    }
}
