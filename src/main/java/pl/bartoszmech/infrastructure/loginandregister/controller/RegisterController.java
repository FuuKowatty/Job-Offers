package pl.bartoszmech.infrastructure.loginandregister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bartoszmech.domain.accountidentifier.AccountIdentifierFacade;
import pl.bartoszmech.domain.accountidentifier.dto.RegistrationResultDto;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.RegisterRequestDto;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
public class RegisterController {
    private final AccountIdentifierFacade loginAndRegisterFacade;
    PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@Valid @RequestBody RegisterRequestDto registerUserDto) {
        RegistrationResultDto registerResult = loginAndRegisterFacade.register(new UserDto(registerUserDto.username(), passwordEncoder.encode(registerUserDto.password())));
        return ResponseEntity.status(CREATED).body(registerResult);
    }
}
