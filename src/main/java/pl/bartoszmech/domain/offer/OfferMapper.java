package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

class OfferMapper {
    public static OfferResponse mapFromOffer(Offer offer) {
        return OfferResponse
                .builder()
                .id(offer.id())
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .jobUrl(offer.jobUrl())
                .createdAt(offer.createdAt())
                .build();
    }

    public static Offer mapToOffer(OfferResponse offer) {
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

    public static Offer mapToOfferWithoutId(OfferRequest offer) {
        return  Offer
                .builder()
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .jobUrl(offer.jobUrl())
                .build();
    }
}
