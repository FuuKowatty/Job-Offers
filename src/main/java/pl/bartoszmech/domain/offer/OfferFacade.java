package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import pl.bartoszmech.domain.offer.dto.CreateOfferDtoResult;
import pl.bartoszmech.domain.offer.dto.InputOfferDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
public class OfferFacade {
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";
    public static final String URL_TAKEN = "Url is already taken";

    OfferValidator validator;
    OfferRepository repository;
    OfferFetcher fetcher;
    public CreateOfferDtoResult createOffer(InputOfferDto offerDto) {
        String title = offerDto.title();
        String company = offerDto.company();
        String salary = offerDto.salary();
        String url = offerDto.jobUrl();

        if(validator.validate(title, company, salary)) {
            return CreateOfferDtoResult
                    .builder()
                    .message(FAILURE)
                    .build();
        }
        if(repository.isExistsByUrl(url)) {
            throw new UrlAlreadyExistsException(URL_TAKEN);
        }
        Offer savedOffer = repository.save(
                Offer.builder()
                        .title(title)
                        .company(company)
                        .salary(salary)
                        .jobUrl(url)
                        .build()
        );

        return CreateOfferDtoResult
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

    public Set<OfferDto> fetchAllOfferAndSaveAllIfNotExists() {
        Set<OfferDto> fetchedOffers = fetcher.fetch();
        repository.deleteAll();
        fetchedOffers.stream().map(offer -> repository.save(OfferMapper.mapToOffer(offer)));
        return fetchedOffers;
    }
}

