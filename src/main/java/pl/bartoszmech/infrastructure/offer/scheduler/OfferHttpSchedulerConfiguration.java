package pl.bartoszmech.infrastructure.offer.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="api.offer.http.schedule.enabled", matchIfMissing = false)
public class OfferHttpSchedulerConfiguration {
}
