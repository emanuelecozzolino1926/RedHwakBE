package emanueleCozzolino.redhawkbe.repositories;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, UUID>, JpaSpecificationExecutor<Ordine> {

    List<Ordine> findByStato(StatoOrdine stato);

    List<Ordine> findByNumeroTavolo(int numeroTavolo);

    @Query("SELECT o FROM Ordine o WHERE o.dataCreazione >= :inizio AND o.dataCreazione < :fine")
    List<Ordine> findByDataCreazioneRange(@Param("inizio") LocalDateTime inizio, @Param("fine") LocalDateTime fine);

}
