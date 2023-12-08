package pl.bartoszmech.infrastructure.offer.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.bartoszmech.domain.offer.OfferNotFoundException;

import java.util.Collections;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Log4j2
public class OfferErrorHandler {
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    public ResponseEntity<OfferNotFoundErrorResponse> handleOfferNotFoundError(OfferNotFoundException e) {
       String message = e.getMessage();
       log.error("OfferNotFoundException with message " + message);
       return ResponseEntity.status(NOT_FOUND).body(new OfferNotFoundErrorResponse(message));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResponseEntity<OfferDuplicateKeys> offerDuplicate() {
        final String message = "Offer url already exists";
        log.error("DuplicateKeyException with message " + message);
        return ResponseEntity.status(CONFLICT).body(new OfferDuplicateKeys(Collections.singletonList(message)));
    }
}
