package pl.bartoszmech.infrastructure.validationhandler;

import java.util.List;

public record ValidationResponse(List<String> messages) {
}
