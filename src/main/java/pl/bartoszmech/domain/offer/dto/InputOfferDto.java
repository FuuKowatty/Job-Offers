package pl.bartoszmech.domain.offer.dto;

import lombok.Builder;

@Builder
public record InputOfferDto(String title, String company, String salary, String jobUrl) {
}
