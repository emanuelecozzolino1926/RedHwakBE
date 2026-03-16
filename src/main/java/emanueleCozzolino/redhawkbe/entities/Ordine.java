package emanueleCozzolino.redhawkbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import emanueleCozzolino.redhawkbe.enums.StatoOrdine;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ordini")
public class Ordine {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int numeroTavolo;

    @Column(nullable = false)
    private LocalDateTime dataCreazione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoOrdine stato;

    private String note;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("ordine")
    private List<VoceOrdine> voci = new ArrayList<>();

    public Ordine() {}

    public Ordine(int numeroTavolo, String note) {
        this.numeroTavolo = numeroTavolo;
        this.note = note;
        this.stato = StatoOrdine.INVIATO;
        this.dataCreazione = LocalDateTime.now();
    }

    public UUID getId() { return id; }

    public int getNumeroTavolo() { return numeroTavolo; }
    public void setNumeroTavolo(int numeroTavolo) { this.numeroTavolo = numeroTavolo; }

    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }

    public StatoOrdine getStato() { return stato; }
    public void setStato(StatoOrdine stato) { this.stato = stato; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<VoceOrdine> getVoci() { return voci; }
    public void setVoci(List<VoceOrdine> voci) { this.voci = voci; }
}
