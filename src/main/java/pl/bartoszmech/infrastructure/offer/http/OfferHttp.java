package pl.bartoszmech.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.bartoszmech.domain.offer.OfferFetcher;
import pl.bartoszmech.domain.offer.dto.OfferApiResponse;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AllArgsConstructor
@Log4j2
public class OfferHttp implements OfferFetcher {
    private static final String SERVICE_ENDPOINT = "/offers";
    RestTemplate restTemplate;
    String uri;
    int port;

    @Override
    public List<OfferApiResponse> handleFetchOffers() {
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(createHeader());
        try {
            List<OfferApiResponse> fetchedOffers = fetchOffers(requestEntity);
            if(fetchedOffers == null) {
                log.error("Response body was null.");
                throw new ResponseStatusException(NO_CONTENT);
            }
            return fetchedOffers;
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers");
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    private List<OfferApiResponse> fetchOffers(HttpEntity<HttpHeaders> requestEntity) {
        ResponseEntity<List<OfferApiResponse>> response = restTemplate.exchange(
                createUrl(),
                GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }
    private String createUrl() {
        return UriComponentsBuilder.fromHttpUrl(getUrlTemplate()).toUriString();
    }
    private String getUrlTemplate() {
        return uri + ":" + port + SERVICE_ENDPOINT;
    }
}
