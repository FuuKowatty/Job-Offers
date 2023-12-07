package pl.bartoszmech.domain.offer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OfferRepository extends MongoRepository<Offer, String> {
    boolean existsByJobUrl(String url);
}
