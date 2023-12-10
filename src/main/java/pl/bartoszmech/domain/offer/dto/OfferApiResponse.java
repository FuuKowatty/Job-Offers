package pl.bartoszmech.domain.offer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record OfferApiResponse(
        String title,
        String company,
        String salary,
        @JsonProperty("offerUrl") String jobUrl
) {
}