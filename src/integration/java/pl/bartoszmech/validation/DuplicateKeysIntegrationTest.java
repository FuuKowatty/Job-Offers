package pl.bartoszmech.validation;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import pl.bartoszmech.BaseIntegrationTest;
import pl.bartoszmech.infrastructure.SampleApiBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DuplicateKeysIntegrationTest extends BaseIntegrationTest implements SampleApiBody {
    @Test
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
