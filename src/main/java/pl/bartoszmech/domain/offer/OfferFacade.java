package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;


@AllArgsConstructor
@Log4j2
public class OfferFacade {
    OfferRepository repository;
    OfferFetcher fetcher;
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

    public List<OfferResponse> listOffers() {
        List<Offer> offers = repository.findAll();
        return offers
                .stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public OfferResponse getOfferById(String id) {
        Offer offer = repository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
        return OfferMapper.mapFromOffer(offer);
    }

    public List<OfferResponse> fetchAllOfferAndSaveAllIfNotExists() {
        log.info("Starting fetch offers...");
        List<OfferRequest> fetchedOffersDto = fetcher.handleFetchOffers();
        log.info("Offers fetched successfully");
        List<Offer> fetchedOffers = fetchedOffersDto
                .stream()
                .map(offerDto -> OfferMapper.mapToOfferWithoutId(offerDto))
                .toList();

        List<Offer> notExistingInDatabaseOffers = filterNotExistingOffers(fetchedOffers);
        log.info("Adding non existing offers to database...");
        repository
                .saveAll(notExistingInDatabaseOffers)
                .stream()
                .map(offer -> OfferMapper.mapFromOffer(offer))
                .toList();
        log.info("Offers successfully saved in database!");
        return notExistingInDatabaseOffers.stream().map(offer -> OfferMapper.mapFromOffer(offer)).toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> fetchedOffers) {
        return fetchedOffers
                .stream()
                .filter(offer -> !offer.jobUrl().isBlank())
                .filter(offer -> !repository.existsByJobUrl(offer.jobUrl()))
                .toList();
    }
}

