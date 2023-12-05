package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

public class OfferMapper {
    public static OfferDto mapFromOffer(Offer offer) {
        return OfferDto
                .builder()
                .id(offer.id())
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .created_at(offer.createdAt())
                .build();
    }
}
