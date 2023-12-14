package pl.bartoszmech.domain.shared;

public class ResourceNotFoundException extends RuntimeException {
    ResourceNotFoundException(String id) {
        super("Offer cannot be found with id: " + id);
    }
}
