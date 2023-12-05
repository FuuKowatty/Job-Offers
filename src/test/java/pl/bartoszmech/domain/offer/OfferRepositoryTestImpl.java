package pl.bartoszmech.domain.offer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryImpl implements OfferRepository {
    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer offer) {
        database.put(offer.id(), offer);
        return offer;
    }
}
