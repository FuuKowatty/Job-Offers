package pl.bartoszmech.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bartoszmech.domain.offer.OfferFacade;
import pl.bartoszmech.domain.offer.dto.OfferApiDto;
import pl.bartoszmech.domain.offer.dto.OfferDto;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferController {
    OfferFacade offerFacade;
    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferApiDto offer ) {
        return ResponseEntity.ok(offerFacade.createOffer(offer));
    }
}
