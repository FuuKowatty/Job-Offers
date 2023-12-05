package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.Set;

interface OfferFetcher {
    Set<OfferDto> fetch();
}
