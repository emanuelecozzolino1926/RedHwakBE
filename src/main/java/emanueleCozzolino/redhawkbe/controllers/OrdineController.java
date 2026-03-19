package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.payload.AggiornaStatoOrdineDTO;
import emanueleCozzolino.redhawkbe.payload.NuovoOrdineDTO;
import emanueleCozzolino.redhawkbe.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ordini")
public class OrdineController {

    private final OrdineService ordineService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public OrdineController(OrdineService ordineService, SimpMessagingTemplate messagingTemplate) {
        this.ordineService = ordineService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUCINA')")
    public List<Ordine> findAll() {
        return ordineService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUCINA')")
    public Ordine findById(@PathVariable UUID id) {
        return ordineService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ordine creaOrdine(@RequestBody @Validated NuovoOrdineDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        Ordine nuovoOrdine = ordineService.creaOrdine(body);
        messagingTemplate.convertAndSend("/topic/ordini", nuovoOrdine);
        return nuovoOrdine;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUCINA')")
    public Ordine update(@PathVariable UUID id, @RequestBody @Validated NuovoOrdineDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return ordineService.update(id, body);
    }

    @PatchMapping("/{id}/stato")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUCINA')")
    public Ordine aggiornaStato(@PathVariable UUID id, @RequestBody @Validated AggiornaStatoOrdineDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        Ordine ordineAggiornato = ordineService.aggiornaStato(id, body);
        messagingTemplate.convertAndSend("/topic/ordini", ordineAggiornato);
        return ordineAggiornato;
    }
}
