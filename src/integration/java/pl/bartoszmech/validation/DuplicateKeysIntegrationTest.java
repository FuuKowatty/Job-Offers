package pl.bartoszmech.validation;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.bartoszmech.BaseIntegrationTest;
import pl.bartoszmech.infrastructure.SampleApiBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DuplicateKeysIntegrationTest extends BaseIntegrationTest implements SampleApiBody {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @WithMockUser
    public void should_return_conflict_when_added_second_offer_with_this_same_url() throws Exception {
        // step 1
        // given && when
        ResultActions firstResponseAfterCreateOffer = mockMvc.perform(post("/offers")
                .content(bodyWithOneOfferJson())
                .contentType(APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        firstResponseAfterCreateOffer.andExpect(status().isCreated());


        // step 2
        // given && when
        ResultActions secondResponseAfterCreateOffer = mockMvc.perform(post("/offers")
                .content(bodyWithOneOfferJson())
                .contentType(APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        secondResponseAfterCreateOffer.andExpect(status().isConflict());
    }
}
