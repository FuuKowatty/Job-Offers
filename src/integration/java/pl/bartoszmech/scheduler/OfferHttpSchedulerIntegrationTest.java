package pl.bartoszmech.scheduler;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.bartoszmech.BaseIntegrationTest;
import pl.bartoszmech.JobOffersApplication;
import pl.bartoszmech.domain.offer.OfferFetcher;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@SpringBootTest(classes = JobOffersApplication.class, properties = "api.offer.http.schedule.enabled=true")
public class OfferHttpSchedulerIntegrationTest extends BaseIntegrationTest {
    @SpyBean
    OfferFetcher offerHttp;
    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        await().
                atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(offerHttp, times(1)).handleFetchOffers());
    }
}
