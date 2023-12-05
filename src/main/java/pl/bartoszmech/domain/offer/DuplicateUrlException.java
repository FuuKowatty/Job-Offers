package pl.bartoszmech.domain.offer;

public class DuplicateUrlException extends RuntimeException {

    DuplicateUrlException(String id) {
        super("Value with such url already exists: " + id);
    }
}
