package pl.bartoszmech.infrastructure.loginandregister.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "{username.not.blank}")
        String username,

        @NotBlank(message = "{password.not.blank}")
        String password
) {
}
