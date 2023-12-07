package pl.bartoszmech.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.bartoszmech.domain.offer.OfferFacade;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferController {
    OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity<List<OfferDto>> findOffers() {
        return ResponseEntity.ok(offerFacade.listOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> findOfferById(@PathVariable("id") String id) {
        return ResponseEntity.ok(offerFacade.getOfferById(id));
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferApiDto inputOffer ) {
        OfferDto offer = offerFacade.createOffer(inputOffer);
        return ResponseEntity.created(getLocation(offer.id())).body(offer);
    }

    private URI getLocation(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
