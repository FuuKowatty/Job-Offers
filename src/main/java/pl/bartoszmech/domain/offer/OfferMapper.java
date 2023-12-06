package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

class OfferMapper {
    public static OfferDto mapFromOffer(Offer offer) {
        return OfferDto
                .builder()
                .id(offer.id())
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .jobUrl(offer.jobUrl())
                .createdAt(offer.createdAt())
                .build();
    }

    public static Offer mapToOffer(OfferDto offer) {
        return  Offer
                .builder()
                .id(offer.id())
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .jobUrl(offer.jobUrl())
                .createdAt(offer.createdAt())
                .build();
    }

    public static Offer mapToOfferWithoutId(OfferApiDto offer) {
        return  Offer
                .builder()
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .jobUrl(offer.jobUrl())
                .build();
    }
}
