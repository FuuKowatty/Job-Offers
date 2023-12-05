package pl.bartoszmech.domain.accountidentifier;

class UserAlreadyExistsException extends RuntimeException {
    UserAlreadyExistsException(String message) {
        super(message);
    }
}
