package pl.bartoszmech.domain.offer.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OfferDto(String id, String title, String company, String salary, LocalDateTime created_at) {
}
