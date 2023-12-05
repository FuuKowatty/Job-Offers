package pl.bartoszmech.domain.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.bartoszmech.domain.offer.OfferValidator.*;

import org.junit.jupiter.api.Test;
import pl.bartoszmech.domain.offer.dto.InputOfferResultDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.Set;

class OfferFacadeTest {
    OfferFacade configurator = new OfferConfiguration().createForTest(new OfferValidator(), new OfferRepositoryTestImpl(), new HashGeneratorTestImpl());
    @Test
    public void should_return_success_message_on_add_offer() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
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
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
        //then
        assertThat(result).isInstanceOf(InputOfferResultDto.class);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.company()).isEqualTo(company);
        assertThat(result.salary()).isEqualTo(salary);
    }
    @Test void should_return_failure_on_empty_title() {
        //given
        String title = "";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        //when
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
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
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
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
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
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
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
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
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
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
        InputOfferResultDto result = configurator.createOffer(title, company, salary, url);
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

    @Test void should_return_success_on_list_offers() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        String url = "https://example-site.com";
        InputOfferResultDto inputOfferResultDto = configurator.createOffer(title, company, salary, url);

        //when
        Set<OfferDto> offerList = configurator.listOffers();
        //then
        OfferDto offerDto = OfferDto.builder()
                .id(inputOfferResultDto.id())
                .title(inputOfferResultDto.title())
                .company(inputOfferResultDto.company())
                .salary(inputOfferResultDto.salary())
                .created_at(inputOfferResultDto.createdAt())
                .jobUrl(inputOfferResultDto.jobUrl())
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
        InputOfferResultDto inputOfferResultDto = configurator.createOffer(title, company, salary, url);
        //when
        OfferDto offerDto = configurator.getOfferById("123");
        //then
        OfferDto expectedOfferDto = OfferDto.builder()
                .id(inputOfferResultDto.id())
                .title(inputOfferResultDto.title())
                .company(inputOfferResultDto.company())
                .salary(inputOfferResultDto.salary())
                .created_at(inputOfferResultDto.createdAt())
                .jobUrl(inputOfferResultDto.jobUrl())
                .build();
        assertThat(offerDto).isEqualTo(expectedOfferDto);
    }


    private String createString(int length) {
        return "x".repeat(length);
    }
}
