package emanueleCozzolino.redhawkbe.controllers;

import emanueleCozzolino.redhawkbe.payload.StatisticheResponseDTO;
import emanueleCozzolino.redhawkbe.service.StatisticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistiche")
public class StatisticheController {

    private final StatisticheService statisticheService;

    @Autowired
    public StatisticheController(StatisticheService statisticheService) {
        this.statisticheService = statisticheService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatisticheResponseDTO getStatistiche() {
        return statisticheService.getStatisticheOggi();
    }
}
