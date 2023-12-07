package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferRequest;

import java.util.List;

public class OfferFetcherTestImpl implements OfferFetcher{
    @Override
    public List<OfferRequest> handleFetchOffers() {
        return List.of(
                OfferRequest.builder().title("Junior Java Developer").company("randomCompany").salary("1000EURO").jobUrl("https://example.com").build(),
                OfferRequest.builder().title("Junior Java Developer").company("randomCompany").salary("800EURO").jobUrl("https://example1.com").build(),
                OfferRequest.builder().title("Junior Java Developer").company("randomCompany").salary("2000EURO").jobUrl("https://example2.com").build()
        );
    }
}
