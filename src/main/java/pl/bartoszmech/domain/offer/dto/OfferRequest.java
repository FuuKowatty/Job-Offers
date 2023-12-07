package pl.bartoszmech.domain.offer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record OfferRequest(
        @NotNull(message = "{title.not.null}")
        @NotBlank(message = "{title.not.empty}")
        @Size(max = 50, message = "{title.size}")
        String title,

        @NotNull(message = "{company.not.null}")
        @NotBlank(message = "{company.not.empty}")
        @Size(max = 50, message = "{company.size}")
        String company,

        @NotNull(message = "{salary.not.null}")
        @NotBlank(message = "{salary.not.empty}")
        @Size(max = 25, message = "{salary.size}")
        String salary,

        @NotNull(message = "{jobUrl.not.null}")
        @NotBlank(message = "{jobUrl.not.empty}")
        @Size(max = 255, message = "{jobUrl.size}")
        @JsonProperty("offerUrl") String jobUrl
) { }
