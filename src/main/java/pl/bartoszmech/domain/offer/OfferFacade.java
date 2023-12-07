package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.bartoszmech.domain.offer.dto.CreateOfferDtoResponse;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Log4j2
public class OfferFacade {
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";

    OfferValidator validator;
    OfferRepository repository;
    OfferFetcher fetcher;
    public OfferDto createOffer(OfferApiDto offerDto) {
        String title = offerDto.title();
        String company = offerDto.company();
        String salary = offerDto.salary();
        String url = offerDto.jobUrl();

//        if(validator.validate(title, company, salary)) {
//            return CreateOfferDtoResponse
//                    .builder()
//                    .message(FAILURE)
//                    .build();
//        }
//        if(repository.existsByJobUrl(url)) {
//            throw new DuplicateUrlException(url);
//        }
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

    public Set<OfferDto> listOffers() {
        List<Offer> offers = repository.findAll();
        return offers
                .stream()
                .map(OfferMapper::mapFromOffer)
                .collect(Collectors.toSet());
    }

    public OfferDto getOfferById(String id) {
        Offer offer = repository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
        OfferDto userDto = OfferMapper.mapFromOffer(offer);
        return userDto;
    }

    public List<OfferDto> fetchAllOfferAndSaveAllIfNotExists() {
        log.info("Starting fetch offers...");
        List<OfferApiDto> fetchedOffersDto = fetcher.handleFetchOffers();
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

