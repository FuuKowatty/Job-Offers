package pl.bartoszmech.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.bartoszmech.domain.offer.OfferService;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;

@Component
@AllArgsConstructor
public class OfferHttpScheduler {

    private final OfferService offerService;

    @Scheduled(cron = "${api.offer.http.schedule.time}")
    public List<OfferResponse> fetchAllOfferAndSaveAllIfNotExists() {
        return offerService.fetchAllOfferAndSaveAllIfNotExists();
    }

}
