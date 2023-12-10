package pl.bartoszmech.domain.offer;

import pl.bartoszmech.domain.offer.dto.OfferApiResponse;

import java.util.List;

public class OfferFacadeTestConfiguration {
    OfferRepositoryTestImpl repository;
    OfferFetcher fetcher;

    OfferFacadeTestConfiguration() {
        this.fetcher = new OfferFetcherTestImpl(
                List.of(
                        new OfferApiResponse("Junior Java Developer", "RandomCompany", "100EURO", "https://someurl.pl/5"),
                        new OfferApiResponse("Junior Fullstack Developer", "RandomCompany", "2000EURO", "https://someurl.pl/6")
                )
        );
        this.repository = new OfferRepositoryTestImpl();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(repository, new OfferService(fetcher, repository));
    }
}
