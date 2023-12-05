package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Set;

interface OfferRepository {
    Offer save(Offer offer);
    List<Offer> findAll();
    Offer findById(String id);
    boolean isExistsByUrl(String url);
    List<Offer> saveAll(List<Offer> offers);
}
