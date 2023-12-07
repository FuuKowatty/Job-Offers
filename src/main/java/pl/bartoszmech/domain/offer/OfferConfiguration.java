package pl.bartoszmech.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferConfiguration {

    @Bean
    OfferFacade offerFacade(OfferRepository repository, OfferFetcher fetcher) {
        return new OfferFacade(repository, fetcher);
    }

    OfferFacade createForTests(OfferRepository repository, OfferFetcher fetcher) {
        return offerFacade(repository, fetcher);
    }
}
