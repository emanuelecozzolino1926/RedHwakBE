package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RicettaItemDTO(
        @NotNull(message = "L'id dell'ingrediente è obbligatorio")
        UUID ingredienteId,
        @Min(value = 1, message = "La quantità richiesta deve essere almeno 1")
        int quantitaRichiesta
) {
}
