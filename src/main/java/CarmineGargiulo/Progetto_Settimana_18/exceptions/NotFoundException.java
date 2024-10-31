package CarmineGargiulo.Progetto_Settimana_18.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id, String resource) {
        super(resource + " with id " + id + " does not exists");
    }
}
