package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import pl.bartoszmech.domain.offer.dto.CreateOfferDtoResponse;
import pl.bartoszmech.domain.offer.dto.InputOfferDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
public class OfferFacade {
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";

    OfferValidator validator;
    OfferRepository repository;
    OfferFetcher fetcher;
    public CreateOfferDtoResponse createOffer(InputOfferDto offerDto) {
        String title = offerDto.title();
        String company = offerDto.company();
        String salary = offerDto.salary();
        String url = offerDto.jobUrl();

        if(validator.validate(title, company, salary)) {
            return CreateOfferDtoResponse
                    .builder()
                    .message(FAILURE)
                    .build();
        }
        if(repository.isExistsByUrl(url)) {
            throw new DuplicateUrlException(url);
        }
        Offer savedOffer = repository.save(
                Offer.builder()
                        .title(title)
                        .company(company)
                        .salary(salary)
                        .jobUrl(url)
                        .build()
        );

        return CreateOfferDtoResponse
                .builder()
                .message(SUCCESS)
                .id(savedOffer.id())
                .title(savedOffer.title())
                .company(savedOffer.company())
                .salary(savedOffer.salary())
                .createdAt(savedOffer.createdAt())
                .jobUrl(savedOffer.jobUrl())
                .build();
    }

    public Set<OfferDto> listOffers() {
        List<Offer> offers = repository.findAll();
        return offers
                .stream()
                .map(OfferMapper::mapFromOffer)
                .collect(Collectors.toSet());
    }

    public OfferDto getOfferById(String id) {
        OfferDto userDto = OfferMapper.mapFromOffer(repository.findById(id));
        return userDto;
    }

    public List<OfferDto> fetchAllOfferAndSaveAllIfNotExists() {
        List<OfferDto> fetchedOffersDto = fetcher.fetch();
        List<Offer> fetchedOffers = fetchedOffersDto
                .stream()
                .map(offerDto -> OfferMapper.mapToOffer(offerDto))
                .toList();

        List<Offer> notExistingInDatabaseOffers = filterNotExistingOffers(fetchedOffers);
        return repository
                .saveAll(notExistingInDatabaseOffers)
                .stream()
                .map(offer -> OfferMapper.mapFromOffer(offer))
                .toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> fetchedOffers) {
        return fetchedOffers
                .stream()
                .filter(offer -> !offer.jobUrl().isBlank())
                .filter(offer -> !repository.isExistsByUrl(offer.jobUrl()))
                .toList();
    }
}

