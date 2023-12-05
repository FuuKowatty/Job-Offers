package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import pl.bartoszmech.domain.offer.dto.InputOfferResultDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
public class OfferFacade {
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";
    OfferValidator validator;
    OfferRepository repository;
    HashGenerator hashGenerator;
    OfferFetcher fetcher;
    public InputOfferResultDto createOffer(String title, String company, String salary, String url) {
        if(validator.validate(title, company, salary)) {
            return InputOfferResultDto
                    .builder()
                    .message(FAILURE)
                    .build();
        }

        if(repository.isNotExistsByUrl(url)) {
            return InputOfferResultDto
                    .builder()
                    .message(FAILURE)
                    .build();
        }

        String id = hashGenerator.getHash();
        LocalDateTime createdAt = LocalDateTime.now();
        Offer savedOffer = repository.save(
                Offer.builder()
                        .id(id)
                        .title(title)
                        .company(company)
                        .salary(salary)
                        .jobUrl(url)
                        .createdAt(createdAt)
                        .build()
        );

        return InputOfferResultDto
                .builder()
                .message(SUCCESS)
                .id(savedOffer.id())
                .title(savedOffer.title())
                .company(savedOffer.company())
                .salary(savedOffer.salary())
                .createdAt(savedOffer.createdAt())
                .jobUrl(url)
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
        return OfferMapper.mapFromOffer(repository.findById(id));
    }

    public Set<OfferDto> fetchAllOfferAndSaveAllIfNotExists() {
        Set<OfferDto> fetchedOffers = fetcher.fetch();
        repository.deleteAll();
        fetchedOffers.stream().map(offer -> repository.save(OfferMapper.mapToOffer(offer)));
        return fetchedOffers;
    }
}

