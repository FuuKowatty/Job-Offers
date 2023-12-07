package pl.bartoszmech.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartoszmech.domain.offer.OfferFacade;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferController {
    OfferFacade offerFacade;

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> findOfferById(@PathVariable("id") String id) {
        return ResponseEntity.ok(offerFacade.getOfferById(id));
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferApiDto offer ) {
        return ResponseEntity.ok(offerFacade.createOffer(offer));
    }
}
