package CarmineGargiulo.Progetto_Settimana_18.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("L'utente con id " + id + " non esiste");
    }
}
