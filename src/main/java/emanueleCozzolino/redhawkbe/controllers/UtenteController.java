package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Utente;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.payload.AggiornaUtenteDTO;
import emanueleCozzolino.redhawkbe.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @PutMapping("/utenti/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente update(@PathVariable UUID id, @RequestBody @Validated AggiornaUtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return utenteService.updateUtente(id, body);
    }

    @DeleteMapping("/utenti/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        utenteService.deleteUtente(id);
    }
}
