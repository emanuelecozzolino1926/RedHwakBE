package emanueleCozzolino.redhawkbe.runners;

import emanueleCozzolino.redhawkbe.entities.Ruolo;
import emanueleCozzolino.redhawkbe.payload.RegistraUtenteDTO;
import emanueleCozzolino.redhawkbe.repositories.RuoloRepository;
import emanueleCozzolino.redhawkbe.repositories.UtenteRepository;
import emanueleCozzolino.redhawkbe.service.UtenteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataRunner implements CommandLineRunner {

    private final RuoloRepository ruoloRepository;
    private final UtenteRepository utenteRepository;
    private final UtenteService utenteService;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    public DataRunner(RuoloRepository ruoloRepository, UtenteRepository utenteRepository, UtenteService utenteService) {
        this.ruoloRepository = ruoloRepository;
        this.utenteRepository = utenteRepository;
        this.utenteService = utenteService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (ruoloRepository.findByNomeRuolo("ADMIN").isEmpty()) {
            ruoloRepository.save(new Ruolo("ADMIN"));
        }

        if (ruoloRepository.findByNomeRuolo("CUCINA").isEmpty()) {
            ruoloRepository.save(new Ruolo("CUCINA"));
        }

        if (utenteRepository.findByUsername(adminUsername).isEmpty()) {

            // Uso le variabili caricate dalle ENV
            RegistraUtenteDTO adminDto = new RegistraUtenteDTO(
                    "Admin",
                    "RedHawk",
                    adminEmail,
                    adminUsername,
                    adminPassword,
                    "ADMIN"
            );

            utenteService.registraUtente(adminDto);
            System.out.println("----------------------------------------");
            System.out.println("Utente ADMIN creato dalle variabili d'ambiente!");
            System.out.println("----------------------------------------");
        }
    }
}
