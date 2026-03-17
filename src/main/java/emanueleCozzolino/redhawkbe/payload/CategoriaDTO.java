package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        @NotBlank(message = "Il nome della categoria è obbligatorio")
        String nome,
        String descrizione,
        String immagineUrl
) {
}
