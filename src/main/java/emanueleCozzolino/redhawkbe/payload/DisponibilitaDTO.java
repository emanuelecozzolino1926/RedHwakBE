package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.NotNull;

public record DisponibilitaDTO(
        @NotNull(message = "Il campo disponibile è obbligatorio")
        Boolean disponibile
) {
}
