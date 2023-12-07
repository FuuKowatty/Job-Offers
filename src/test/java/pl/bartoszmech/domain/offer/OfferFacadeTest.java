package pl.bartoszmech.domain.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;

class OfferFacadeTest {
    OfferFacade configurator = new OfferConfiguration().createForTests(new OfferRepositoryTestImpl(), new OfferFetcherTestImpl());
    @Test
    public void should_return_success_message_on_add_offer() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        OfferResponse result = configurator.createOffer(OfferRequest
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertAll(
                () -> assertThat(result.title()).isEqualTo(title),
                () -> assertThat(result.company()).isEqualTo(company),
                () -> assertThat(result.salary()).isEqualTo(salary),
                () -> assertThat(result.jobUrl()).isEqualTo(url)
        );
    }

    @Test void should_throw_exception_on_create_offer_with_taken_url() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        configurator.repository.save(Offer
                .builder()
                .id("1")
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //when&then
        assertThrows(DuplicateKeyException.class, () -> configurator.createOffer(OfferRequest
                .builder()
                .title("some title")
                .company("somem company")
                .salary("no money")
                .jobUrl(url)
                .build()
        ));
    }

    @Test void should_fetch_new_offers() {
        //given&when
        List<OfferResponse> fetchedOffers = configurator.fetchAllOfferAndSaveAllIfNotExists();

        //then
        List<OfferResponse> expectedOffers = List.of(
                OfferResponse.builder().title("Junior Java Developer").company("randomCompany").salary("1000EURO").jobUrl("https://example.com").build(),
                OfferResponse.builder().title("Junior Java Developer").company("randomCompany").salary("800EURO").jobUrl("https://example1.com").build(),
                OfferResponse.builder().title("Junior Java Developer").company("randomCompany").salary("2000EURO").jobUrl("https://example2.com").build()
        );
        assertThat(fetchedOffers.size()).isEqualTo(expectedOffers.size());
    }

    private String createString(int length) {
        return "x".repeat(length);
    }
}
