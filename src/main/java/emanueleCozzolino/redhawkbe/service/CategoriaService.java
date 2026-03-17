package emanueleCozzolino.redhawkbe.service;

import emanueleCozzolino.redhawkbe.entities.Categoria;
import emanueleCozzolino.redhawkbe.exceptions.BadRequestException;
import emanueleCozzolino.redhawkbe.exceptions.NotFoundException;
import emanueleCozzolino.redhawkbe.payload.CategoriaDTO;
import emanueleCozzolino.redhawkbe.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(UUID id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new NotFoundException("La categoria con id: " + id + " non è stata trovata!"));
    }

    public Categoria save(CategoriaDTO dto) {
        if (categoriaRepository.findByNome(dto.nome()).isPresent()) throw new BadRequestException("La categoria " + dto.nome() + " esiste già!");

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescrizione(dto.descrizione());
        categoria.setImmagineUrl(dto.immagineUrl());

        return categoriaRepository.save(categoria);
    }

    public Categoria update(UUID id, CategoriaDTO dto) {
        Categoria categoria = this.findById(id);
        categoria.setNome(dto.nome());
        categoria.setDescrizione(dto.descrizione());
        categoria.setImmagineUrl(dto.immagineUrl());

        return categoriaRepository.save(categoria);
    }

    public void delete(UUID id) {
        Categoria categoria = this.findById(id);
        categoriaRepository.delete(categoria);
    }
}
