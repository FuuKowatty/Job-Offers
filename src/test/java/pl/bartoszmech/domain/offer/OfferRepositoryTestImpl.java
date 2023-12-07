package pl.bartoszmech.domain.offer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class OfferRepositoryTestImpl implements OfferRepository {
    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer entity) {
        if(database.values().stream().anyMatch(offer -> entity.jobUrl().equals(offer.jobUrl()))) {
            throw new DuplicateUrlException(entity.jobUrl());
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Offer offer = Offer
                .builder()
                .id(id)
                .title(entity.title())
                .company(entity.company())
                .salary(entity.salary())
                .jobUrl(entity.jobUrl())
                .createdAt(createdAt)
                .build();
        database.put(id, offer);
        return offer;
    }

    @Override
    public Optional<Offer> findById(String string) {
        return Optional.ofNullable(database.get(string));
    }

    @Override
    public boolean existsById(String string) {
        return false;
    }


    public boolean existsByJobUrl(String url) {
        long count = database
                .values()
                .stream()
                .filter(offer -> offer.jobUrl().equals(url))
                .count();
        return count == 1;
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> offers) {
        offers.forEach(this::save);
        // Filter and collect the saved offers of type S
        return database.values()
                .stream()
                .filter(offer -> offers.iterator().next().getClass().isInstance(offer))
                .map(offer -> (S) offer)
                .toList();
    }

    @Override
    public List<Offer> findAll() {
        return database
                .values()
                .stream()
                .toList();
    }

    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String string) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }
}
