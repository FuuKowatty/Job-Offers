package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.Set;

public interface OfferFetcher {
    Set<OfferDto> fetch();
}
