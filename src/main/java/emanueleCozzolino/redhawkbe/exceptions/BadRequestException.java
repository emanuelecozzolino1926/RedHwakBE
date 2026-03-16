package emanueleCozzolino.redhawkbe.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String messaggio) {
        super(messaggio);
    }
}
