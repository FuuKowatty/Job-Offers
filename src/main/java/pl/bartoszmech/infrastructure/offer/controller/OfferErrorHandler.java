package pl.bartoszmech.infrastructure.offer.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.bartoszmech.domain.offer.OfferNotFoundException;

@ControllerAdvice
@Log4j2
public class OfferErrorHandler {
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    public ResponseEntity<OfferNotFoundErrorResponse> handleOfferNotFoundError(OfferNotFoundException e) {
       String message = e.getMessage();
       log.error("OfferNotFoundException with message " + message);
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OfferNotFoundErrorResponse(message)
       );
    }
}
