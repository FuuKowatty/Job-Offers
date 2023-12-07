package pl.bartoszmech.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import pl.bartoszmech.domain.offer.OfferFacade;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;

@Component
@AllArgsConstructor
public class OfferHttpScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(cron = "${api.offer.http.schedule.time}")
    public List<OfferDto> fetchOffer() {
        return offerFacade.fetchAllOfferAndSaveAllIfNotExists();
    }

}
