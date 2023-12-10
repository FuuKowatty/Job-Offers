package pl.bartoszmech.infrastructure.loginandregister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bartoszmech.domain.accountidentifier.AccountIdentifierFacade;
import pl.bartoszmech.domain.accountidentifier.dto.RegistrationResultDto;
import pl.bartoszmech.domain.accountidentifier.dto.UserDto;
import pl.bartoszmech.infrastructure.loginandregister.controller.dto.RegisterRequest;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final AccountIdentifierFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@Valid @RequestBody RegisterRequest registerUserDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(registerUserDto.password());
        RegistrationResultDto registerResult = loginAndRegisterFacade.register(
                new UserDto(registerUserDto.username(), encodedPassword));
        return ResponseEntity.status(CREATED).body(registerResult);
    }
}
