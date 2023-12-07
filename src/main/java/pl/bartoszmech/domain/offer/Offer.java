package pl.bartoszmech.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Document("offers")
record Offer(
        @Id String id,
        String title,
        String company,
        String salary,
        @CreatedDate @Field("created_at") LocalDateTime createdAt,
        @Field("url") @Indexed(unique = true) String jobUrl) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id) && Objects.equals(title, offer.title) && Objects.equals(company, offer.company) && Objects.equals(salary, offer.salary) && Objects.equals(createdAt, offer.createdAt) && Objects.equals(jobUrl, offer.jobUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, company, salary, createdAt, jobUrl);
    }
}
