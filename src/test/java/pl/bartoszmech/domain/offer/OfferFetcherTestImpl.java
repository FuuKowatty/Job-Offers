package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;

public class OfferFetcherTestImpl implements OfferFetcher{
    @Override
    public List<OfferDto> fetch() {
        return List.of(
                OfferDto.builder().title("Junior Java Developer").company("randomCompany").salary("1000EURO").jobUrl("https://example.com").build(),
                OfferDto.builder().title("Junior Java Developer").company("randomCompany").salary("800EURO").jobUrl("https://example1.com").build(),
                OfferDto.builder().title("Junior Java Developer").company("randomCompany").salary("2000EURO").jobUrl("https://example2.com").build()
        );
    }
}
