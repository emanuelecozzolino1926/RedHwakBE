package emanueleCozzolino.redhawkbe.payload;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record StatisticheResponseDTO(
        long ordiniTotaliOggi,
        BigDecimal incassoOggi,
        Map<String, Long> ordiniPerStato,
        List<ProdottoPopolareDTO> topProdotti) {
}
