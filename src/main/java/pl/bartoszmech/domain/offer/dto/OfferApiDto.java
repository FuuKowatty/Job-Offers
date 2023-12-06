package pl.bartoszmech.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferApiDto(String title, String company, String salary, String jobUrl) {
}
