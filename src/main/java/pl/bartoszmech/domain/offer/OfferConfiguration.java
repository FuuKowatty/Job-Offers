package pl.bartoszmech.domain.offer;

public class OfferConfiguration {
    OfferFacade createForTests(OfferValidator validator, OfferRepository repository, HashGenerator generator, OfferFetcher fetcher) {
        return new OfferFacade(validator, repository, generator, fetcher);
    }
}
