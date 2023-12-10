package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.bartoszmech.domain.offer.dto.OfferApiResponse;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;
import java.util.Objects;

@Log4j2
@AllArgsConstructor
@Service
public class OfferService {
    OfferFetcher fetcher;
    OfferRepository repository;
    public List<OfferResponse> fetchAllOfferAndSaveAllIfNotExists() {
        log.info("Starting fetch offers...");
        List<OfferApiResponse> fetchedOffersDto = fetcher.handleFetchOffers();

        log.info("Offers fetched successfully");
        List<Offer> fetchedOffers = fetchedOffersDto
                .stream()
                .map(offerDto -> OfferMapper.mapToOfferFromApiResponse(offerDto))
                .toList();

        List<Offer> notExistingInDatabaseOffers = filterNotExistingOffers(fetchedOffers);
        log.info("Adding non existing offers to database...");
        List<OfferResponse> savedOffers = repository
                .saveAll(notExistingInDatabaseOffers)
                .stream()
                .map(offer -> OfferMapper.mapFromOffer(offer))
                .toList();
        log.info("Offers successfully saved in database!");
        return savedOffers.stream()
                .filter(offer -> notExistingInDatabaseOffers.stream()
                        .anyMatch(notExistingOffer -> Objects.equals(offer.jobUrl(), notExistingOffer.jobUrl())))
                .toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> fetchedOffers) {
        return fetchedOffers
                .stream()
                .filter(offer -> !offer.jobUrl().isBlank())
                .filter(offer -> !repository.existsByJobUrl(offer.jobUrl()))
                .toList();
    }
}
