package pl.bartoszmech.domain.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;

class OfferFacadeTest {
    @Test
    public void should_fetch_from_jobs_from_remote_and_save_all_offers_when_repository_is_empty() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.listOffers()).isEmpty();

        // when
        List<OfferResponse> result = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    public void should_save_only_2_offers_when_repository_had_4_added_with_offer_urls() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "1"));
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "2"));
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "3"));
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "4"));
        assertThat(offerFacade.listOffers()).hasSize(4);

        // when
        List<OfferResponse> response = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        // then
        System.out.println(response.get(0).jobUrl());
        System.out.println(response.get(1).jobUrl());
        assertThat(List.of(
                        response.get(0).jobUrl(),
                        response.get(1).jobUrl()
                )
        ).containsExactlyInAnyOrder("https://someurl.pl/5", "https://someurl.pl/6");
    }

    @Test
    public void should_save_4_offers_when_there_are_no_offers_in_database() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();

        // when
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "1"));
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "2"));
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "3"));
        offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "4"));

        // then
        assertThat(offerFacade.listOffers()).hasSize(4);
    }

    @Test
    public void should_find_offer_by_id_when_offer_was_saved() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        OfferResponse offerResponseDto = offerFacade.createOffer(new OfferRequest("asds", "id", "asdasd", "1"));
        // when
        OfferResponse offerById = offerFacade.getOfferById(offerResponseDto.id());

        // then
        assertAll(
                () -> assertEquals(offerResponseDto.id(), offerById.id()),
                () -> assertEquals("id", offerById.company()),
                () -> assertEquals("asds", offerById.title()),
                () -> assertEquals("asdasd", offerById.salary()),
                () -> assertEquals("1", offerById.jobUrl())
        );
    }

    @Test
    public void should_throw_not_found_exception_when_offer_not_found() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.listOffers()).isEmpty();

        // when
        Throwable thrown = catchThrowable(() -> offerFacade.getOfferById("100"));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer cannot be found with id: 100");
    }

    @Test
    public void should_throw_duplicate_key_exception_when_with_offer_url_exists() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        OfferResponse offerResponseDto = offerFacade.createOffer(new OfferRequest("id", "asds", "asdasd", "hello.pl"));
        String savedId = offerResponseDto.id();
        assertThat(offerFacade.getOfferById(savedId).id()).isEqualTo(savedId);
        // when
        Throwable thrown = catchThrowable(() -> offerFacade.createOffer(
                new OfferRequest("cx", "vc", "xcv", "hello.pl")));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("hello.pl");
    }
}
