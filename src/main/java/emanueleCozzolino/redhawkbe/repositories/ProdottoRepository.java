package emanueleCozzolino.redhawkbe.repositories;

import emanueleCozzolino.redhawkbe.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {

    List<Prodotto> findByCategoriaId(UUID categoriaId);

    List<Prodotto> findByDisponibile(boolean disponibile);

}
