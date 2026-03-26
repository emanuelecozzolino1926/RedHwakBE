package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.payload.AggiornaStatoOrdineDTO;
import emanueleCozzolino.redhawkbe.payload.NuovoOrdineDTO;
import emanueleCozzolino.redhawkbe.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public Page<Ordine> findAll(
            @RequestParam(required = false) StatoOrdine stato,
            @RequestParam(required = false) Integer tavolo,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("dataCreazione").descending());
        if (stato != null || tavolo != null || from != null) {
            return ordineService.findWithFilters(stato, tavolo, from, pageable);
        }
        return ordineService.findAll(pageable);
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
        messagingTemplate.convertAndSend("/topic/ordini/nuovi", nuovoOrdine);
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
        messagingTemplate.convertAndSend("/topic/ordini/aggiornamenti", ordineAggiornato);
        return ordineAggiornato;
    }
}
