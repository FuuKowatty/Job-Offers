package pl.bartoszmech.domain.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.bartoszmech.domain.offer.dto.InputOfferResultDto;

class OfferFacadeTest {
    OfferFacade offerFacade = new OfferFacade(new OfferValidator(), new OfferRepositoryTestImpl(), new HashGeneratorImpl());
    @Test
    public void should_return_success_message_on_add_offer() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        //when
        InputOfferResultDto result = offerFacade.createOffer(title, company, salary);
        //then
        assertThat(result.message()).isEqualTo("success");
    }

    @Test void should_return_offer_on_add_offer() {
        //given
        String title = "Junior Java Developer";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        //when
        InputOfferResultDto offer = offerFacade.createOffer(title, company, salary);
        //then
        assertThat(offer).isInstanceOf(InputOfferResultDto.class);
        assertThat(offer.title()).isEqualTo(title);
        assertThat(offer.company()).isEqualTo(company);
        assertThat(offer.salary()).isEqualTo(salary);
    }
    @Test void should_return_failure_on_empty_title() {
        //given
        String title = "";
        String company = "Capcake";
        String salary = "0.00 - 1.00 USD";
        //when
        InputOfferResultDto result = offerFacade.createOffer(title, company, salary);
        //then
        assertThat(result.message()).isEqualTo("failure");
    }

}
