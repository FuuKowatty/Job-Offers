package pl.bartoszmech.domain.offer;

public class OfferConfiguration {
    OfferFacade createForTest(OfferValidator validator, OfferRepository repository, HashGenerator generator) {
        return new OfferFacade(validator, repository, generator);
    }
}
