package pl.bartoszmech.domain.accountidentifier;

class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String message) {
        super(message);
    }
}
