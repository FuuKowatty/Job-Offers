package pl.bartoszmech.domain.accountidentifier.dto;

import lombok.Builder;

@Builder
public record UserDto(String username, String password) {
}
