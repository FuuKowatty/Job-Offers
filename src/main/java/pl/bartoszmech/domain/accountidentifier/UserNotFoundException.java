package pl.bartoszmech.domain.accountidentifier;

class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String username) {
        super("Offer cannot be found with username: " + username);
    }
}
