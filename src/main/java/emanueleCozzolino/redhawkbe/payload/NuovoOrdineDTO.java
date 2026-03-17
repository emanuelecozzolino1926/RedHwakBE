package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NuovoOrdineDTO(
        @NotNull(message = "Il numero tavolo è obbligatorio")
        @Min(value = 1, message = "Il numero tavolo deve essere almeno 1")
        Integer numeroTavolo,
        String note,
        @NotEmpty(message = "L'ordine deve contenere almeno un prodotto")
        @Valid
        List<VoceOrdineDTO> voci
) {
}
