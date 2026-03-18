package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import emanueleCozzolino.redhawkbe.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cucina")
public class KitchenController {

    private final OrdineService ordineService;

    @Autowired
    public KitchenController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @GetMapping("/ordini")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUCINA')")
    public List<Ordine> getOrdiniAttivi() {
        return ordineService.findByStato(StatoOrdine.INVIATO);
    }

    @GetMapping("/ordini/in-preparazione")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUCINA')")
    public List<Ordine> getOrdiniInPreparazione() {
        return ordineService.findByStato(StatoOrdine.IN_PREPARAZIONE);
    }
}
