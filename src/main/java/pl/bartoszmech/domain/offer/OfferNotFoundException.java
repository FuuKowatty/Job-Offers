package pl.bartoszmech.domain.offer;

public class OfferNotFoundException extends RuntimeException {
    OfferNotFoundException(String id) {
        super("Offer cannot be found with id: " + id);
    }
}
