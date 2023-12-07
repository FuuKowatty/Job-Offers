package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferRequest;

import java.util.List;

public interface OfferFetcher {
    List<OfferRequest>  handleFetchOffers();
}
