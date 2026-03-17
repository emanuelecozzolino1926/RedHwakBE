package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Ruolo;
import emanueleCozzolino.redhawkbe.entities.Utente;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.exceptions.NotFoundException;
import emanueleCozzolino.redhawkbe.payload.RegistraUtenteDTO;
import emanueleCozzolino.redhawkbe.repositories.RuoloRepository;
import emanueleCozzolino.redhawkbe.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public Utente findById(UUID id) {
        return this.utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("L'utente con id: " + id + " non è stato trovato!"));
    }

    public Utente findByUsername(String username) {
        return this.utenteRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("L'utente con username: " + username + " non è stato trovato!"));
    }

    public Utente registraUtente(RegistraUtenteDTO dto) {
        if (utenteRepository.findByEmail(dto.email()).isPresent()) throw new BadRequestException("Email " + dto.email() + " già in uso!");
        if (utenteRepository.findByUsername(dto.username()).isPresent()) throw new BadRequestException("Username " + dto.username() + " già in uso!");

        Utente utente = new Utente();
        utente.setNome(dto.nome());
        utente.setCognome(dto.cognome());
        utente.setEmail(dto.email());
        utente.setUsername(dto.username());
        utente.setPassword(passwordEncoder.encode(dto.password()));

        Ruolo ruolo = ruoloRepository.findByNomeRuolo(dto.ruolo())
                .orElseThrow(() -> new NotFoundException("Ruolo " + dto.ruolo() + " non trovato nel DB!"));
        Set<Ruolo> ruoli = new HashSet<>();
        ruoli.add(ruolo);
        utente.setRuoli(ruoli);

        return utenteRepository.save(utente);
    }

    public void deleteUtente(UUID id) {
        Utente utente = this.findById(id);
        this.utenteRepository.delete(utente);
    }
}
