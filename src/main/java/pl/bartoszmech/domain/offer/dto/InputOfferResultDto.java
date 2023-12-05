package pl.bartoszmech.domain.offer.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InputOfferResultDto(String message, String id, String title, String company, String salary, LocalDateTime createdAt, String jobUrl) {
}
