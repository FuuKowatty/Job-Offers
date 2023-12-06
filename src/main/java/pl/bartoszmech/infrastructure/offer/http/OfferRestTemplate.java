package pl.bartoszmech.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.bartoszmech.domain.offer.OfferFetcher;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.util.List;

@AllArgsConstructor
public class OfferRestTemplate {
    RestTemplate restTemplate;
//    private final String URL = "";
//    @Override
//    public ResponseEntity<List<OfferDto>> fetch(HttpEntity<HttpHeaders> requestEntity) {
//        ResponseEntity<List<OfferDto>> response = restTemplate.exchange(
//                URL,
//                HttpMethod.GET,
//                requestEntity,
//                new ParameterizedTypeReference<>() {
//
//                });
//        return response;
//    }
}
