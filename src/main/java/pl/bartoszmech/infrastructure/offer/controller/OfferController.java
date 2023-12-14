package pl.bartoszmech.infrastructure.offer.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartoszmech.domain.offer.OfferFacade;
import pl.bartoszmech.domain.offer.dto.OfferRequest;
import pl.bartoszmech.domain.offer.dto.OfferResponse;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferController {
    OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity<List<OfferResponse>> findOffers() {
        return ResponseEntity.status(OK).body(offerFacade.listOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponse> findOfferById(@PathVariable("id") String id) {
        return ResponseEntity.status(OK).body(offerFacade.getOfferById(id));
    }

    @PostMapping
    public ResponseEntity<OfferResponse> createOffer(@RequestBody @Valid OfferRequest inputOffer ) {
        return ResponseEntity.status(CREATED).body(offerFacade.createOffer(inputOffer));
    }

}
