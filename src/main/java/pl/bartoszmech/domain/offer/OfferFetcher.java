package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Set;

public interface OfferFetcher {
    List<OfferDto> fetch();
}
