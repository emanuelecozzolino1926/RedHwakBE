package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import emanueleCozzolino.redhawkbe.payload.ProdottoPopolareDTO;
import emanueleCozzolino.redhawkbe.payload.StatisticheResponseDTO;
import emanueleCozzolino.redhawkbe.repositories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticheService {

    private final OrdineRepository ordineRepository;

    @Autowired
    public StatisticheService(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    public StatisticheResponseDTO getStatisticheOggi() {
        LocalDateTime inizioGiorno = LocalDate.now().atStartOfDay();
        LocalDateTime fineGiorno = inizioGiorno.plusDays(1);

        List<Ordine> ordiniOggi = ordineRepository.findByDataCreazioneRange(inizioGiorno, fineGiorno);

        long totaleOrdini = ordiniOggi.size();

        BigDecimal incasso = ordiniOggi.stream()
                .filter(o -> !o.getStato().equals(StatoOrdine.ANNULLATO))
                .flatMap(o -> o.getVoci().stream())
                .map(v -> v.getPrezzoUnitario().multiply(BigDecimal.valueOf(v.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Long> ordiniPerStato = ordiniOggi.stream()
                .collect(Collectors.groupingBy(o -> o.getStato().toString(), Collectors.counting()));

        Map<String, Long> conteggioPerProdotto = ordiniOggi.stream()
                .filter(o -> !o.getStato().equals(StatoOrdine.ANNULLATO))
                .flatMap(o -> o.getVoci().stream())
                .collect(Collectors.groupingBy(
                        v -> v.getProdotto().getNome(),
                        Collectors.summingLong(v -> v.getQuantita())));

        List<ProdottoPopolareDTO> topProdotti = conteggioPerProdotto.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(e -> new ProdottoPopolareDTO(e.getKey(), e.getValue()))
                .toList();

        return new StatisticheResponseDTO(totaleOrdini, incasso, ordiniPerStato, topProdotti);
    }
}
