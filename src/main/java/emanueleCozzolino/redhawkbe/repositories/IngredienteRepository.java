package emanueleCozzolino.redhawkbe.repositories;

import emanueleCozzolino.redhawkbe.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, UUID> {

    Optional<Ingrediente> findByNome(String nome);

}
