package emanueleCozzolino.redhawkbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "voci_ordine")
public class VoceOrdine {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ordine_id", nullable = false)
    @JsonIgnoreProperties("voci")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", nullable = false)
    @JsonIgnoreProperties({"ricetta", "categoria"})
    private Prodotto prodotto;

    @Column(nullable = false)
    private int quantita;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoUnitario;

    public VoceOrdine() {}

    public VoceOrdine(Ordine ordine, Prodotto prodotto, int quantita) {
        this.ordine = ordine;
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoUnitario = prodotto.getPrezzo();
    }

    public UUID getId() { return id; }

    public Ordine getOrdine() { return ordine; }
    public void setOrdine(Ordine ordine) { this.ordine = ordine; }

    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public BigDecimal getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(BigDecimal prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
}
