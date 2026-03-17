package emanueleCozzolino.redhawkbe.payload;

import java.time.LocalDateTime;

public record ErrorsPayload(String messaggio, LocalDateTime timestamp) {}
