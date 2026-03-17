package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdottoDTO(
        @NotBlank(message = "Il nome del prodotto è obbligatorio")
        String nome,
        String descrizione,
        @NotNull(message = "Il prezzo è obbligatorio")
        @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0")
        BigDecimal prezzo,
        String immagineUrl,
        @NotNull(message = "La categoria è obbligatoria")
        UUID categoriaId
) {
}
