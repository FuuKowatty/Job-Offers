package pl.bartoszmech.infrastructure.validationhandler;

import lombok.AllArgsConstructor;

import java.util.List;

public record ValidationResponse(List<String> messages) {
}
