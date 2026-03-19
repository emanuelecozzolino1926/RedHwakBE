package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Ingrediente;
import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.entities.Prodotto;
import emanueleCozzolino.redhawkbe.entities.ProdottoIngrediente;
import emanueleCozzolino.redhawkbe.entities.VoceOrdine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.exceptions.NotFoundException;
import emanueleCozzolino.redhawkbe.payload.AggiornaStatoOrdineDTO;
import emanueleCozzolino.redhawkbe.payload.NuovoOrdineDTO;
import emanueleCozzolino.redhawkbe.repositories.IngredienteRepository;
import emanueleCozzolino.redhawkbe.repositories.OrdineRepository;
import emanueleCozzolino.redhawkbe.repositories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrdineService {

    private final OrdineRepository ordineRepository;

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

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

        try {
            for (var voceDto : dto.voci()) {
                Prodotto prodotto = prodottoService.findById(voceDto.prodottoId());
                if (!prodotto.isDisponibile()) throw new BadRequestException("Il prodotto " + prodotto.getNome() + " non è al momento disponibile!");

                VoceOrdine voce = new VoceOrdine(ordine, prodotto, voceDto.quantita());
                ordine.getVoci().add(voce);

                for (ProdottoIngrediente pi : prodotto.getRicetta()) {
                    Ingrediente ingrediente = pi.getIngrediente();
                    int quantitaDaScalare = pi.getQuantitaRichiesta() * voceDto.quantita();

                    if (ingrediente.getGiacenza() < quantitaDaScalare) throw new BadRequestException("Giacenza insufficiente per l'ingrediente: " + ingrediente.getNome());

                    ingrediente.setGiacenza(ingrediente.getGiacenza() - quantitaDaScalare);
                    ingredienteRepository.save(ingrediente);

                    if (ingrediente.getGiacenza() == 0) {
                        List<Prodotto> prodottiColleati = prodottoRepository.findAll().stream()
                                .filter(p -> p.getRicetta().stream()
                                        .anyMatch(r -> r.getIngrediente().getId().equals(ingrediente.getId())))
                                .toList();
                        prodottiColleati.forEach(p -> {
                            p.setDisponibile(false);
                            prodottoRepository.save(p);
                        });
                    }
                }
            }
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new BadRequestException("Ordine non completato: uno o più prodotti sono appena esauriti, riprova!");
        }

        return ordineRepository.save(ordine);
    }

    public Ordine aggiornaStato(UUID id, AggiornaStatoOrdineDTO dto) {
        Ordine ordine = this.findById(id);
        ordine.setStato(dto.stato());
        return ordineRepository.save(ordine);
    }

    public Ordine update(UUID id, NuovoOrdineDTO dto) {
        Ordine ordine = this.findById(id);
        if (!ordine.getStato().equals(StatoOrdine.INVIATO)) throw new BadRequestException("L'ordine non può essere modificato perché è già in preparazione!");

        ordine.getVoci().clear();
        ordine.setNumeroTavolo(dto.numeroTavolo());
        ordine.setNote(dto.note());

        for (var voceDto : dto.voci()) {
            Prodotto prodotto = prodottoService.findById(voceDto.prodottoId());
            if (!prodotto.isDisponibile()) throw new BadRequestException("Il prodotto " + prodotto.getNome() + " non è al momento disponibile!");

            VoceOrdine voce = new VoceOrdine(ordine, prodotto, voceDto.quantita());
            ordine.getVoci().add(voce);
        }

        return ordineRepository.save(ordine);
    }
}
