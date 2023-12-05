package pl.bartoszmech.domain.offer;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
record Offer(String id, String title, String company, String salary, LocalDateTime createdAt, String jobUrl) {
}
