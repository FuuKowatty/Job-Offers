package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository {
    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer entity) {
        if(database.values().stream().anyMatch(offer -> entity.jobUrl().equals(offer.jobUrl()))) {
            throw new DuplicateUrlException(entity.jobUrl());
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Offer offer = Offer
                .builder()
                .id(id)
                .title(entity.title())
                .company(entity.company())
                .salary(entity.salary())
                .jobUrl(entity.jobUrl())
                .createdAt(createdAt)
                .build();
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

    public boolean isExistsByUrl(String url) {
        long count = database
                .values()
                .stream()
                .filter(offer -> offer.jobUrl().equals(url))
                .count();
        return count == 1;
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        offers.forEach(offer -> save(offer));
        return findAll();
    }
}
