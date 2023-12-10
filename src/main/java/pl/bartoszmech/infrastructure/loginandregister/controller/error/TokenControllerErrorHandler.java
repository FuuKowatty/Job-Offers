package pl.bartoszmech.infrastructure.loginandregister.controller.error;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class TokenControllerErrorHandler {
    private static final String BAD_CREDENTIALS = "Bad Credentials";
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<TokenErrorResponse> handleBadCredentials() {
        return ResponseEntity.status(UNAUTHORIZED).body(new TokenErrorResponse(BAD_CREDENTIALS));
    }
}
