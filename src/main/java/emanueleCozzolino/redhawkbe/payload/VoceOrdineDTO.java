package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VoceOrdineDTO(
        @NotNull(message = "L'id del prodotto è obbligatorio")
        UUID prodottoId,
        @Min(value = 1, message = "La quantità deve essere almeno 1")
        int quantita
) {
}
