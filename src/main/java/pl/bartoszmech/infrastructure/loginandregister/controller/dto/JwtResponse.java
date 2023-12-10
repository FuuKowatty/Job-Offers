package pl.bartoszmech.infrastructure.loginandregister.controller.dto;

import lombok.Builder;

@Builder
public record JwtResponse(
        String username,
        String token
) {
}
