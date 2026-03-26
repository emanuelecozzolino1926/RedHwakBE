package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Ingrediente;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.exceptions.NotFoundException;
import emanueleCozzolino.redhawkbe.payload.IngredienteDTO;
import emanueleCozzolino.redhawkbe.repositories.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    @Autowired
    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente findById(UUID id) {
        return ingredienteRepository.findById(id).orElseThrow(() -> new NotFoundException("L'ingrediente con id: " + id + " non è stato trovato!"));
    }

    public Ingrediente save(IngredienteDTO dto) {
        if (ingredienteRepository.findByNome(dto.nome()).isPresent()) throw new BadRequestException("L'ingrediente " + dto.nome() + " esiste già!");

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(dto.nome());
        ingrediente.setGiacenza(dto.giacenza());

        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente update(UUID id, IngredienteDTO dto) {
        Ingrediente ingrediente = this.findById(id);
        ingrediente.setNome(dto.nome());
        ingrediente.setGiacenza(dto.giacenza());

        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> findSottoSoglia(int soglia) {
        return ingredienteRepository.findByGiacenzaLessThanEqual(soglia);
    }

    public void delete(UUID id) {
        Ingrediente ingrediente = this.findById(id);
        ingredienteRepository.delete(ingrediente);
    }
}
