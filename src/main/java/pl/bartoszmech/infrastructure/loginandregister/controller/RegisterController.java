package pl.bartoszmech.infrastructure.loginandregister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bartoszmech.domain.accountidentifier.AccountIdentifierFacade;
import pl.bartoszmech.domain.accountidentifier.dto.RegistrationResultDto;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.RegisterRequestDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import pl.bartoszmech.infrastructure.security.jwt.JwtAuthenticatorFacade;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class RegisterController {
    private final AccountIdentifierFacade loginAndRegisterFacade;
    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        final JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequestDto);
        return ResponseEntity.ok(jwtResponseDto);
    }
    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@Valid @RequestBody RegisterRequestDto registerUserDto) {
        RegistrationResultDto registerResult = loginAndRegisterFacade.register(new UserDto(registerUserDto.username(), passwordEncoder.encode(registerUserDto.password())));
        return ResponseEntity.status(CREATED).body(registerResult);
    }
}
