package pl.bartoszmech.domain.accountidentifier;

class AccountIdentifierConfiguration {
    AccountIdentifierFacade createForTests(AccountIdentifierRepository repository) {
        return new AccountIdentifierFacade(repository);
    }
}
