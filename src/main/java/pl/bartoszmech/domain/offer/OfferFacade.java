package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;
import pl.bartoszmech.domain.shared.ResourceNotFoundException;

import java.util.List;


@AllArgsConstructor
@Log4j2
public class OfferFacade {
    OfferRepository repository;
    OfferService service;
    public OfferResponse createOffer(OfferRequest offerDto) {
        String title = offerDto.title();
        String company = offerDto.company();
        String salary = offerDto.salary();
        String url = offerDto.jobUrl();

        Offer savedOffer = repository.save(
                Offer.builder()
                        .title(title)
                        .company(company)
                        .salary(salary)
                        .jobUrl(url)
                        .build()
        );
        return OfferMapper.mapFromOffer(savedOffer);
    }

    @Cacheable(cacheNames = "jobOffers")
    public List<OfferResponse> listOffers() {
        List<Offer> offers = repository.findAll();
        return offers
                .stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public OfferResponse getOfferById(String id) {
        Offer offer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return OfferMapper.mapFromOffer(offer);
    }

    public List<OfferResponse> fetchAllOffersAndSaveAllIfNotExists() {
        return service.fetchAllOfferAndSaveAllIfNotExists();
    }
}

