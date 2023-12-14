package pl.bartoszmech.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferConfiguration {
    @Bean
    OfferFacade offerFacade(OfferRepository repository, OfferFetcher fetcher, OfferService service) {
        OfferService offerService = new OfferService(fetcher, repository);
        return new OfferFacade(repository, offerService);
    }
}
