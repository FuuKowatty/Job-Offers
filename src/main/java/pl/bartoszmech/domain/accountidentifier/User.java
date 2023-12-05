package pl.bartoszmech.domain.accountidentifier;

import lombok.Builder;

@Builder
record User(String username, String password) {
}
