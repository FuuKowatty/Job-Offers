package pl.bartoszmech.domain.offer;

import java.time.LocalDateTime;

record Offer(String id, String title, String company, String salary, LocalDateTime created_at) {
}
