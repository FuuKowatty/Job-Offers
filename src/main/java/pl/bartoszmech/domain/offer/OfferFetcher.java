package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferApiResponse;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;

public interface OfferFetcher {
    List<OfferApiResponse> handleFetchOffers();
}
