package pl.bartoszmech.infrastructure.loginandregister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import pl.bartoszmech.infrastructure.security.jwt.JwtAuthenticatorFacade;

@RestController
@AllArgsConstructor
@Log4j2
public class TokenController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequest) {
        final JwtResponseDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
