package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Prodotto;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.payload.DisponibilitaDTO;
import emanueleCozzolino.redhawkbe.payload.ProdottoDTO;
import emanueleCozzolino.redhawkbe.payload.RicettaItemDTO;
import emanueleCozzolino.redhawkbe.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prodotti")
public class ProdottoController {

    private final ProdottoService prodottoService;

    @Autowired
    public ProdottoController(ProdottoService prodottoService) {
        this.prodottoService = prodottoService;
    }

    @GetMapping
    public List<Prodotto> findAll() {
        return prodottoService.findAll();
    }

    @GetMapping("/{id}")
    public Prodotto findById(@PathVariable UUID id) {
        return prodottoService.findById(id);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Prodotto> findByCategoria(@PathVariable UUID categoriaId) {
        return prodottoService.findByCategoria(categoriaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto save(@RequestBody @Validated ProdottoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return prodottoService.save(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto update(@PathVariable UUID id, @RequestBody @Validated ProdottoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return prodottoService.update(id, body);
    }

    @PostMapping("/{id}/ricetta")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto aggiungiIngrediente(@PathVariable UUID id, @RequestBody @Validated RicettaItemDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return prodottoService.aggiungiIngrediente(id, body);
    }

    @PatchMapping("/{id}/disponibilita")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto aggiornaDisponibilita(@PathVariable UUID id, @RequestBody @Validated DisponibilitaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return prodottoService.aggiornaDisponibilita(id, body);
    }

    @DeleteMapping("/{id}/ricetta/{ingredienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prodotto rimuoviIngrediente(@PathVariable UUID id, @PathVariable UUID ingredienteId) {
        return prodottoService.rimuoviIngrediente(id, ingredienteId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        prodottoService.delete(id);
    }
}
