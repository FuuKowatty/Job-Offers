package pl.bartoszmech.domain.accountidentifier;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String message) {
        super(message);
    }
}
