package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Ingrediente;
import emanueleCozzolino.redhawkbe.entities.Prodotto;
import emanueleCozzolino.redhawkbe.entities.ProdottoIngrediente;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.exceptions.NotFoundException;
import emanueleCozzolino.redhawkbe.payload.DisponibilitaDTO;
import emanueleCozzolino.redhawkbe.payload.ProdottoDTO;
import emanueleCozzolino.redhawkbe.payload.RicettaItemDTO;
import emanueleCozzolino.redhawkbe.repositories.ProdottoIngredienteRepository;
import emanueleCozzolino.redhawkbe.repositories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdottoService {

    private final ProdottoRepository prodottoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private ProdottoIngredienteRepository prodottoIngredienteRepository;

    public ProdottoService(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
    }

    public List<Prodotto> findAll() {
        return prodottoRepository.findAll();
    }

    public List<Prodotto> findByCategoria(UUID categoriaId) {
        return prodottoRepository.findByCategoriaId(categoriaId);
    }

    public Prodotto findById(UUID id) {
        return prodottoRepository.findById(id).orElseThrow(() -> new NotFoundException("Il prodotto con id: " + id + " non è stato trovato!"));
    }

    public Prodotto save(ProdottoDTO dto) {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome(dto.nome());
        prodotto.setDescrizione(dto.descrizione());
        prodotto.setPrezzo(dto.prezzo());
        prodotto.setImmagineUrl(dto.immagineUrl());
        prodotto.setCategoria(categoriaService.findById(dto.categoriaId()));

        return prodottoRepository.save(prodotto);
    }

    public Prodotto update(UUID id, ProdottoDTO dto) {
        Prodotto prodotto = this.findById(id);
        prodotto.setNome(dto.nome());
        prodotto.setDescrizione(dto.descrizione());
        prodotto.setPrezzo(dto.prezzo());
        prodotto.setImmagineUrl(dto.immagineUrl());
        prodotto.setCategoria(categoriaService.findById(dto.categoriaId()));

        return prodottoRepository.save(prodotto);
    }

    public Prodotto aggiungiIngrediente(UUID prodottoId, RicettaItemDTO dto) {
        Prodotto prodotto = this.findById(prodottoId);
        Ingrediente ingrediente = ingredienteService.findById(dto.ingredienteId());

        boolean giaPresente = prodotto.getRicetta().stream()
                .anyMatch(pi -> pi.getIngrediente().getId().equals(dto.ingredienteId()));
        if (giaPresente) throw new BadRequestException("L'ingrediente è già presente nella ricetta di questo prodotto!");

        ProdottoIngrediente pi = new ProdottoIngrediente(prodotto, ingrediente, dto.quantitaRichiesta());
        prodottoIngredienteRepository.save(pi);

        return this.findById(prodottoId);
    }

    public Prodotto rimuoviIngrediente(UUID prodottoId, UUID ingredienteId) {
        Prodotto prodotto = this.findById(prodottoId);

        ProdottoIngrediente pi = prodotto.getRicetta().stream()
                .filter(r -> r.getIngrediente().getId().equals(ingredienteId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ingrediente non trovato nella ricetta di questo prodotto!"));

        prodottoIngredienteRepository.delete(pi);
        return this.findById(prodottoId);
    }

    public Prodotto aggiornaDisponibilita(UUID id, DisponibilitaDTO dto) {
        Prodotto prodotto = this.findById(id);
        prodotto.setDisponibile(dto.disponibile());
        return prodottoRepository.save(prodotto);
    }

    public void delete(UUID id) {
        Prodotto prodotto = this.findById(id);
        prodottoRepository.delete(prodotto);
    }
}
