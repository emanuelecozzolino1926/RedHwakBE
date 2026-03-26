package emanueleCozzolino.redhawkbe.repositories;

import emanueleCozzolino.redhawkbe.entities.Ordine;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, UUID> {

    List<Ordine> findByStato(StatoOrdine stato);

    List<Ordine> findByNumeroTavolo(int numeroTavolo);

    @Query(value = "SELECT o FROM Ordine o WHERE " +
            "(:stato IS NULL OR o.stato = :stato) AND " +
            "(:tavolo IS NULL OR o.numeroTavolo = :tavolo) AND " +
            "(:from IS NULL OR o.dataCreazione >= :from)",
            countQuery = "SELECT COUNT(o) FROM Ordine o WHERE " +
            "(:stato IS NULL OR o.stato = :stato) AND " +
            "(:tavolo IS NULL OR o.numeroTavolo = :tavolo) AND " +
            "(:from IS NULL OR o.dataCreazione >= :from)")
    Page<Ordine> findWithFilters(
            @Param("stato") StatoOrdine stato,
            @Param("tavolo") Integer tavolo,
            @Param("from") LocalDateTime from,
            Pageable pageable
    );

    @Query("SELECT o FROM Ordine o WHERE o.dataCreazione >= :inizio AND o.dataCreazione < :fine")
    List<Ordine> findByDataCreazioneRange(@Param("inizio") LocalDateTime inizio, @Param("fine") LocalDateTime fine);

}
