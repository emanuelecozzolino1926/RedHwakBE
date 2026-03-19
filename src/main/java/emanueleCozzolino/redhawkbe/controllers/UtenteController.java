package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Utente;
import emanueleCozzolino.redhawkbe.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UtenteController {

    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/me")
    public Utente getMe(@AuthenticationPrincipal Utente utente) {
        return this.utenteService.findById(utente.getId());
    }

    @GetMapping("/utenti")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Utente> findAll() {
        return utenteService.findAll();
    }

    @DeleteMapping("/utenti/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        utenteService.deleteUtente(id);
    }
}
