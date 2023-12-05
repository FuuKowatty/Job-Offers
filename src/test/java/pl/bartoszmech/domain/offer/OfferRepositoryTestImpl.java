package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository {
    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer offer) {
        database.put(offer.id(), offer);
        return offer;
    }

    @Override
    public List<Offer> findAll() {
        return database
                .values()
                .stream()
                .toList();
    }

    @Override
    public Offer findById(String id) {
        return database.get(id);
    }

    @Override
    public boolean isExistsByUrl(String url) {
        return database
                .values()
                .stream()
                .filter(offer -> offer.jobUrl() == url)
                .toList()
                .isEmpty();
    }

}
