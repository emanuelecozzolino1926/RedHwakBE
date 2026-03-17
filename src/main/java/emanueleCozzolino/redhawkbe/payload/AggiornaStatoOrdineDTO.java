package emanueleCozzolino.redhawkbe.payload;

import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import jakarta.validation.constraints.NotNull;

public record AggiornaStatoOrdineDTO(
        @NotNull(message = "Il nuovo stato è obbligatorio")
        StatoOrdine stato
) {
}
