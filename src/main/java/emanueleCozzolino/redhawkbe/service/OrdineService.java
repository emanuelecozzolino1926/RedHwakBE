package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.entities.Prodotto;
import emanueleCozzolino.redhawkbe.entities.VoceOrdine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.exceptions.NotFoundException;
import emanueleCozzolino.redhawkbe.payload.AggiornaStatoOrdineDTO;
import emanueleCozzolino.redhawkbe.payload.NuovoOrdineDTO;
import emanueleCozzolino.redhawkbe.repositories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrdineService {

    private final OrdineRepository ordineRepository;

    @Autowired
    private ProdottoService prodottoService;

    public OrdineService(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    public List<Ordine> findAll() {
        return ordineRepository.findAll();
    }

    public Ordine findById(UUID id) {
        return ordineRepository.findById(id).orElseThrow(() -> new NotFoundException("L'ordine con id: " + id + " non è stato trovato!"));
    }

    public List<Ordine> findByStato(StatoOrdine stato) {
        return ordineRepository.findByStato(stato);
    }

    @Transactional
    public Ordine creaOrdine(NuovoOrdineDTO dto) {
        Ordine ordine = new Ordine(dto.numeroTavolo(), dto.note());

        for (var voceDto : dto.voci()) {
            Prodotto prodotto = prodottoService.findById(voceDto.prodottoId());
            if (!prodotto.isDisponibile()) throw new BadRequestException("Il prodotto " + prodotto.getNome() + " non è al momento disponibile!");

            VoceOrdine voce = new VoceOrdine(ordine, prodotto, voceDto.quantita());
            ordine.getVoci().add(voce);
        }

        return ordineRepository.save(ordine);
    }

    public Ordine aggiornaStato(UUID id, AggiornaStatoOrdineDTO dto) {
        Ordine ordine = this.findById(id);
        ordine.setStato(dto.stato());
        return ordineRepository.save(ordine);
    }
}
