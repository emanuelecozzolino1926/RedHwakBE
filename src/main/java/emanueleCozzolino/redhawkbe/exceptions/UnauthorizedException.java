package emanueleCozzolino.redhawkbe.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String messaggio) {
        super(messaggio);
    }
}
