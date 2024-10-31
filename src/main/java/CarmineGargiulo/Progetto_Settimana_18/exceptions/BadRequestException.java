package CarmineGargiulo.Progetto_Settimana_18.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String e) {
        super(e);
    }
}
