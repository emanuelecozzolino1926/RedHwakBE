package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record IngredienteDTO(
        @NotBlank(message = "Il nome dell'ingrediente è obbligatorio")
        String nome,
        @Min(value = 0, message = "La giacenza non può essere negativa")
        int giacenza
) {
}
