package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferApiResponse;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;

public class OfferFetcherTestImpl implements OfferFetcher{
    List<OfferApiResponse> listOfOffers;

    OfferFetcherTestImpl(List<OfferApiResponse> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<OfferApiResponse> handleFetchOffers() {
        return listOfOffers;
    }
}
