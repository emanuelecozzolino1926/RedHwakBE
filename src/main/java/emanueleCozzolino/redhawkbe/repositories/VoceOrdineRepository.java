package emanueleCozzolino.redhawkbe.repositories;

import emanueleCozzolino.redhawkbe.entities.VoceOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoceOrdineRepository extends JpaRepository<VoceOrdine, UUID> {

    List<VoceOrdine> findByOrdineId(UUID ordineId);

}
