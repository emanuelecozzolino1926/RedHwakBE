package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AggiornaUtenteDTO(
        @NotBlank(message = "Il nome è obbligatorio")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "Lo username è obbligatorio")
        String username,

        @NotBlank(message = "Il ruolo è obbligatorio")
        String ruolo) {
}
