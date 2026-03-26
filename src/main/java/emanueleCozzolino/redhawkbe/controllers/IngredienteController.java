package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Ingrediente;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.payload.IngredienteDTO;
import emanueleCozzolino.redhawkbe.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredienti")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @Autowired
    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Ingrediente> findAll() {
        return ingredienteService.findAll();
    }

    @GetMapping("/sotto-soglia")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Ingrediente> findSottoSoglia(@RequestParam(defaultValue = "5") int soglia) {
        return ingredienteService.findSottoSoglia(soglia);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ingrediente findById(@PathVariable UUID id) {
        return ingredienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ingrediente save(@RequestBody @Validated IngredienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return ingredienteService.save(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ingrediente update(@PathVariable UUID id, @RequestBody @Validated IngredienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new BadRequestException(errors.toString());
        }
        return ingredienteService.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        ingredienteService.delete(id);
    }
}
