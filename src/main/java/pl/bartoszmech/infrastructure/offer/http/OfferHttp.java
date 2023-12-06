package pl.bartoszmech.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.bartoszmech.domain.offer.OfferFetcher;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferHttp implements OfferFetcher {
    private static final String SERVICE_ENDPOINT = "/offers";
    RestTemplate restTemplate;
    String uri;
    int port;

    @Override
    public List<OfferApiDto> handleFetchOffers() {
        log.info("START FETCHING");
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(createHeader());
        try {
            List<OfferApiDto> fetchedOffers = fetchOffers(requestEntity);
            if(fetchedOffers == null) {
                log.error("Response body was null.");
                return Collections.emptyList();
            }
            return fetchedOffers;
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers");
            return Collections.emptyList();
        }
    }

    private List<OfferApiDto> fetchOffers(HttpEntity<HttpHeaders> requestEntity) {
        ResponseEntity<List<OfferApiDto>> response = restTemplate.exchange(
                createUrl(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    private String createUrl() {
        return UriComponentsBuilder.fromHttpUrl(getUrlTemplate()).toUriString();
    }
    private String getUrlTemplate() {
        return uri + ":" + port + SERVICE_ENDPOINT;
    }
}