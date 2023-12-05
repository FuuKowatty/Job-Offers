package pl.bartoszmech.domain.offer;

public class OfferConfiguration {
    OfferFacade createForTests(OfferValidator validator, OfferRepository repository, OfferFetcher fetcher) {
        return new OfferFacade(validator, repository, fetcher);
    }
}
