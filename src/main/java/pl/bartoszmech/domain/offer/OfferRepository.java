package pl.bartoszmech.domain.offer;

import java.util.List;

interface OfferRepository {
    Offer save(Offer offer);
    List<Offer> findAll();
    Offer findById(String id);
    boolean isExistsByUrl(String url);
    void deleteAll();
}
