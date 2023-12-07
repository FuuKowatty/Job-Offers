package pl.bartoszmech.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferConfiguration {

    @Bean
    OfferFacade offerFacade(OfferRepository repository, OfferFetcher fetcher) {
        OfferValidator validator = new OfferValidator();
        return new OfferFacade(validator, repository, fetcher);
    }

    OfferFacade createForTests(OfferValidator validator, OfferRepository repository, OfferFetcher fetcher) {
        return offerFacade(repository, fetcher);
    }
}
