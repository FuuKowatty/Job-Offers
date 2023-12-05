package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository {
    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer entity) {
        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Offer offer = Offer
                .builder()
                .id(id)
                .title(entity.title())
                .company(entity.company())
                .salary(entity.salary())
                .createdAt(createdAt).build();
        database.put(id, offer);
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
    public boolean isNotExistsByUrl(String url) {
        return !database
                .values()
                .stream()
                .filter(offer -> offer.jobUrl() == url)
                .toList()
                .isEmpty();
    }

    @Override
    public void deleteAll() {
        database.clear();
    }

}
