package pl.bartoszmech.domain.offer;

class UrlAlreadyExistsException extends RuntimeException{
    UrlAlreadyExistsException(String message) {
        super(message);
    }
}
