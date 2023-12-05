package pl.bartoszmech.domain.offer;

import java.util.List;

public interface OfferRepository {
    Offer save(Offer offer);
    List<Offer> findAll();
}
