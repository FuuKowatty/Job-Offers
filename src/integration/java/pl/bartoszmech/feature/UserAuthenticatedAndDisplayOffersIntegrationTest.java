package pl.bartoszmech.feature;


import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.bartoszmech.BaseIntegrationTest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;
import pl.bartoszmech.infrastructure.SampleApiBody;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.bartoszmech.infrastructure.offer.scheduler.OfferHttpScheduler;



import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class UserAuthenticatedAndDisplayOffersIntegrationTest extends BaseIntegrationTest implements SampleApiBody {
    @Autowired
    OfferHttpScheduler scheduler;
    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void should_be_authenticated_to_display_offer() throws Exception {
    //step 1: there are no offers in external HTTP server
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));



//step 2: scheduler ran 1st time and made GET request to external server and system added 0 offers to database
        //given&when
        List<OfferResponse> offersAfterFirstRun = scheduler.fetchAllOfferAndSaveAllIfNotExists();
        //then
        assertThat(offersAfterFirstRun).isEmpty();


//step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //given&when
        mockMvc
                .perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(APPLICATION_JSON_VALUE))
        // then
                .andExpect(status().isUnauthorized());




//step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //given&when
        mockMvc
                .perform(get("/offers")
                        .contentType(APPLICATION_JSON_VALUE))
        // then
                .andExpect(status().isForbidden());


//step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //given&when
        mockMvc
                .perform(post("/register")
                        .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                        .contentType(APPLICATION_JSON_VALUE))
        // then
                .andExpect(status().isCreated());



//step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        MvcResult loginResponse =
                mockMvc
                    .perform(post("/token")
                            .content("""
                            {
                            "username": "someUser",
                            "password": "somePassword"
                            }
                            """.trim())
                            .contentType(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();
                    // then
        JwtResponseDto jwtResponseDto = objectMapper.readValue(loginResponse.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(jwtResponseDto.username()).isEqualTo("someUser");
        String token = jwtResponseDto.token();
        assertAll(
                () -> assertThat(jwtResponseDto.username()).isEqualTo("someUser"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );


//step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        MvcResult responseWithZeroOffers =
                mockMvc
                    .perform(get("/offers")
                            .header("Authorization", "Bearer " + token)
                            .contentType(APPLICATION_JSON_VALUE))
                    // then
                    .andExpect(status().isOk())
                    .andReturn();
        List<OfferResponse> zeroOffers = objectMapper.readValue(responseWithZeroOffers.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(zeroOffers).isEmpty();



//step 8: there are 2 new offers in external HTTP server
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));


//step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //given&when
        List<OfferResponse> offersAfterSecondRun = scheduler.fetchAllOfferAndSaveAllIfNotExists();
        //then
        assertThat(offersAfterSecondRun.size()).isEqualTo(2);


//step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        //given&when
        MvcResult responseFromListOffers = mockMvc.perform(get("/offers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON_VALUE))
        //then
                        .andExpect(status().isOk())
                        .andReturn();
        List<OfferResponse> listOfTwoOffers = objectMapper.readValue(responseFromListOffers.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(listOfTwoOffers).isEqualTo(offersAfterSecondRun);


//step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
            //given&when
            mockMvc.perform(get("/offers/nonExistingId")
                            .header("Authorization", "Bearer " + token)
                            .contentType(APPLICATION_JSON_VALUE))
            //then
                            .andExpect(status().isNotFound());



//step 12: user made GET /offers/1000 and system returned OK(200) with offer
            //given&when
            MvcResult responseFromGetOrderById = mockMvc.perform(get("/offers/" + listOfTwoOffers.get(0).id())
                            .header("Authorization", "Bearer " + token)
                            .contentType(APPLICATION_JSON_VALUE))
            //then
                            .andExpect(status().isOk())
                            .andReturn();

            OfferResponse offerFoundById = objectMapper.readValue(responseFromGetOrderById.getResponse().getContentAsString(), new TypeReference<>() {});
            assertThat(offerFoundById).isEqualTo(listOfTwoOffers.get(0));


//step 13: there are 2 new offers in external HTTP server
            wireMockServer.stubFor(WireMock.get("/offers")
                    .willReturn(WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(bodyWithFourOffersJson())));


//step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
            //given&when
            List<OfferResponse> offersAfterThirdRun = scheduler.fetchAllOfferAndSaveAllIfNotExists();
            //then
            assertThat(offersAfterSecondRun.size()).isEqualTo(2);


//step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
            //given&when
            MvcResult responseWhichExpectsFourOffers = mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + token)
                            .contentType(APPLICATION_JSON_VALUE))
                    //then
                    .andExpect(status().isOk())
                    .andReturn();

            OfferResponse expectedThirdOffer = offersAfterThirdRun.get(0);
            OfferResponse expectedFourthOffer = offersAfterThirdRun.get(1);
            List<OfferResponse> listOfFourOffers = objectMapper.readValue(responseWhichExpectsFourOffers.getResponse().getContentAsString(), new TypeReference<>() {});
            assertThat(listOfFourOffers).contains(expectedThirdOffer, expectedFourthOffer);



//step 16 Make POST /offers and system returned OK(200) with my new offer
            //given&when
            MvcResult responseFromCreateOffers = mockMvc.perform(post("/offers")
                            .header("Authorization", "Bearer " + token)
                            .content(bodyWithOneOfferJson())
                            .contentType(APPLICATION_JSON_VALUE))
            //then
                    .andExpect(status().isCreated())
                    .andReturn();
            OfferResponse createdOffers = objectMapper.readValue(responseFromCreateOffers.getResponse().getContentAsString(), new TypeReference<>() {});
            assertThat(createdOffers.id()).isNotNull();


//step17 Check if added offer exists
            //given&when
            MvcResult responseExpectingNewOffer = mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + token)
                            .contentType(APPLICATION_JSON_VALUE))
            //then
                            .andExpect(status().isOk())
                            .andReturn();
            List<OfferResponse> listOffers = objectMapper.readValue(responseExpectingNewOffer.getResponse().getContentAsString(), new TypeReference<>() {});
            assertThat(listOffers).hasSize(5);
    }
}
