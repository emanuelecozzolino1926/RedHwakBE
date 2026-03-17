package emanueleCozzolino.redhawkbe.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistraUtenteDTO(
        @NotBlank(message = "Il nome è obbligatorio")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido")

        String email,

        @NotBlank(message = "Lo username è obbligatorio")
        String username,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 6, message = "La password deve essere di almeno 6 caratteri")
        String password,

        @NotBlank(message = "Il ruolo è obbligatorio")
        String ruolo
) {}
