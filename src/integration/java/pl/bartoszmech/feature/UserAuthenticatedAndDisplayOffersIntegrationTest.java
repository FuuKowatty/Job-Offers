package pl.bartoszmech.feature;


import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.bartoszmech.BaseIntegrationTest;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;
import pl.bartoszmech.infrastructure.SampleApiBody;
import pl.bartoszmech.infrastructure.offer.scheduler.OfferHttpScheduler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class UserAuthenticatedAndDisplayOffersIntegrationTest extends BaseIntegrationTest implements SampleApiBody {
    @Autowired
    OfferHttpScheduler scheduler;
    @Test
    public void should_be_authenticated_to_display_offer() {
    //step 1: there are no offers in external HTTP server
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));

//step 2: scheduler ran 1st time and made GET request to external server and system added 0 offers to database
        //given&when
        List<OfferDto> offers = scheduler.fetchAllOfferAndSaveAllIfNotExists();
        //then
        assertThat(offers).isEmpty();
//step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
//step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
//step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
//step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
//step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
//step 8: there are 2 new offers in external HTTP server
//step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
//step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
//step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
//step 12: user made GET /offers/1000 and system returned OK(200) with offer
//step 13: there are 2 new offers in external HTTP server
//step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
//step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
//step 16 Make POST /offers and system returned OK(200) with my new offer
        try {
            //given&when
            MvcResult response = mockMvc.perform(post("/offers")
                            .content(bodyWithOneOfferJson())
                            .contentType(APPLICATION_JSON_VALUE))
                    //then
                    .andExpect(status().isOk())
                    .andReturn();
            String json = response.getResponse().getContentAsString();
            OfferDto offer = objectMapper.readValue(json, OfferDto.class);

            assertThat(offer.id()).isNotNull();
        }
        catch (UnsupportedEncodingException e) {
            log.error("Invalid JSON Format with message: " + e.getMessage());
            throw new RuntimeException();
        }
        catch (Exception e) {
            log.error("Threw exception during perform POST /offers with message: " + e.getMessage());
            throw new RuntimeException();
        }
    }
}
