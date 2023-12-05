package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;

public interface OfferRepository {
    Offer save(Offer offer);
    List<Offer> findAll();
    Offer findById(String id);
}
