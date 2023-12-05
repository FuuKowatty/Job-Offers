package pl.bartoszmech.domain.offer;

import lombok.AllArgsConstructor;
import pl.bartoszmech.domain.offer.dto.InputOfferResultDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
public class OfferFacade {
    OfferValidator validator;
    OfferRepository repository;
    HashGenerator hashGenerator;
    public InputOfferResultDto createOffer(String title, String company, String salary, String url) {
        if(validator.validate(title, company, salary)) {
            return InputOfferResultDto
                    .builder()
                    .message("failure")
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
                .message("success")
                .id(savedOffer.id())
                .title(savedOffer.title())
                .company(savedOffer.company())
                .salary(savedOffer.salary())
                .created_at(savedOffer.createdAt())
                .build();
    }

    public List<OfferDto> listOffers() {
        List<Offer> offers = repository.findAll();
        return offers
                .stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public OfferDto getOfferById(String id) {
        return OfferMapper.mapFromOffer(repository.findById(id));
    }

}

