package pl.bartoszmech.domain.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.bartoszmech.domain.offer.OfferValidator.*;

import org.junit.jupiter.api.Test;
import pl.bartoszmech.domain.offer.dto.CreateOfferDtoResult;
import pl.bartoszmech.domain.offer.dto.InputOfferDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.Set;

class OfferFacadeTest {
    OfferFacade configurator = new OfferConfiguration().createForTests(new OfferValidator(), new OfferRepositoryTestImpl(), new OfferFetcherTestImpl());
    @Test
    public void should_return_success_message_on_add_offer() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("success");
    }

    @Test void should_return_offer_on_add_offer() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result).isInstanceOf(CreateOfferDtoResult.class);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.company()).isEqualTo(company);
        assertThat(result.salary()).isEqualTo(salary);
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
        assertThrows(UrlAlreadyExistsException.class, () -> configurator.createOffer(InputOfferDto
                .builder()
                .title("some title")
                .company("somem company")
                .salary("no money")
                .jobUrl(url)
                .build()
        ));
    }
    @Test void should_return_failure_on_empty_title() {
        //given
        String title = "";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_failure_on_too_long_title() {
        //given
        String  title = createString(MAXIMUM_TITLE_LENGTH+1);
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_failure_on_too_short_title() {
        //given
        String  title = createString(MINIMUM_TITLE_LENGTH-1);
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_failure_on_too_long_company() {
        //given
        String  title = "Junior Java Developer";
        String company = createString(MAXIMUM_COMPANY_LENGTH+1);
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_failure_on_too_short_company() {
        //given
        String  title = "Junior Java Developer";
        String company = createString(MINIMUM_TITLE_LENGTH-1);
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_failure_on_empty_salary() {
        //given
        String  title = "Junior Java Developer";
        String company = createString(MINIMUM_TITLE_LENGTH-1);
        String salary = "";
        String url = "https://example-site.com";
        //when
        CreateOfferDtoResult result = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_success_on_list_offers() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        CreateOfferDtoResult createOfferDtoResult = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        //when
        Set<OfferDto> offerList = configurator.listOffers();
        //then
        OfferDto offerDto = OfferDto.builder()
                .id(createOfferDtoResult.id())
                .title(createOfferDtoResult.title())
                .company(createOfferDtoResult.company())
                .salary(createOfferDtoResult.salary())
                .createdAt(createOfferDtoResult.createdAt())
                .jobUrl(createOfferDtoResult.jobUrl())
                .build();
        Set<OfferDto> expectedOfferList = Set.of(offerDto);
        assertThat(offerList).isEqualTo(expectedOfferList);
    }

    @Test void should_return_offer_by_id() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        CreateOfferDtoResult createOfferDtoResult = configurator.createOffer(InputOfferDto
                .builder()
                .title(title)
                .company(company)
                .salary(salary)
                .jobUrl(url)
                .build()
        );
        String id = createOfferDtoResult.id();
        //when
        OfferDto offerDto = configurator.getOfferById(id);
        //then
        OfferDto expectedOfferDto = OfferDto.builder()
                .id(id)
                .title(createOfferDtoResult.title())
                .company(createOfferDtoResult.company())
                .salary(createOfferDtoResult.salary())
                .createdAt(createOfferDtoResult.createdAt())
                .jobUrl(createOfferDtoResult.jobUrl())
                .build();
        assertThat(offerDto).isEqualTo(expectedOfferDto);
    }

    @Test void should_fetch_new_offers() {
        //given

        //when
        Set<OfferDto> fetchedOffers = configurator.fetchAllOfferAndSaveAllIfNotExists();

        //then
        Set<OfferDto> expectedOffers = Set.of(
                OfferDto.builder().title("Junior Java Developer").company("randomCompany").salary("1000EURO").jobUrl("https://example.com").build(),
                OfferDto.builder().title("Junior Java Developer").company("randomCompany").salary("800EURO").jobUrl("https://example1.com").build(),
                OfferDto.builder().title("Junior Java Developer").company("randomCompany").salary("2000EURO").jobUrl("https://example2.com").build()
        );
        assertThat(fetchedOffers).isEqualTo(expectedOffers);
    }

    private String createString(int length) {
        return "x".repeat(length);
    }
}
