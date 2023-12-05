package pl.bartoszmech.domain.offer;

import java.util.UUID;

 class HashGeneratorImpl implements HashGenerator {

    @Override
    public String getHash() {
        return UUID.randomUUID().toString();
    }
}
