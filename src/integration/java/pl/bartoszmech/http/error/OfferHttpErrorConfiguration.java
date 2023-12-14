package pl.bartoszmech.http.error;

import org.springframework.web.client.RestTemplate;
import pl.bartoszmech.domain.offer.OfferFetcher;
import pl.bartoszmech.infrastructure.offer.http.OfferHttp;
import pl.bartoszmech.infrastructure.offer.http.OfferHttpConfiguration;

import static pl.bartoszmech.BaseIntegrationTest.WIRE_MOCK_HOST;


public class OfferHttpErrorConfiguration extends OfferHttpConfiguration {
    public OfferFetcher offerFetchTest(int port, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return new OfferHttp(restTemplate, WIRE_MOCK_HOST, readTimeout);
    }
}
