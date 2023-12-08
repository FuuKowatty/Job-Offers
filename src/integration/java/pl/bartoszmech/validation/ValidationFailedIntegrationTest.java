package pl.bartoszmech.validation;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.bartoszmech.BaseIntegrationTest;
import pl.bartoszmech.infrastructure.SampleApiBody;
import pl.bartoszmech.infrastructure.validationhandler.ValidationResponse;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValidationFailedIntegrationTest extends BaseIntegrationTest implements SampleApiBody {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_empty_and_null_in_offer_save_request() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content(bodyOfInvalidCreateOfferRequest())
                .contentType(APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ValidationResponse result = objectMapper.readValue(json, ValidationResponse.class);

        assertThat(result.messages()).containsExactlyInAnyOrder(
                "company must not be empty",
                "title must not be empty",
                "salary must not be empty",
                "offerUrl must not be null",
                "offerUrl must not be empty");
    }
}
