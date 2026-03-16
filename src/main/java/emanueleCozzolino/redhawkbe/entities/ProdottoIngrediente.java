package emanueleCozzolino.redhawkbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "prodotto_ingredienti")
public class ProdottoIngrediente {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", nullable = false)
    @JsonIgnoreProperties({"ricetta", "categoria"})
    private Prodotto prodotto;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    @JsonIgnoreProperties("ricette")
    private Ingrediente ingrediente;

    @Column(nullable = false)
    private int quantitaRichiesta;

    public ProdottoIngrediente() {}

    public ProdottoIngrediente(Prodotto prodotto, Ingrediente ingrediente, int quantitaRichiesta) {
        this.prodotto = prodotto;
        this.ingrediente = ingrediente;
        this.quantitaRichiesta = quantitaRichiesta;
    }

    public UUID getId() { return id; }

    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }

    public Ingrediente getIngrediente() { return ingrediente; }
    public void setIngrediente(Ingrediente ingrediente) { this.ingrediente = ingrediente; }

    public int getQuantitaRichiesta() { return quantitaRichiesta; }
    public void setQuantitaRichiesta(int quantitaRichiesta) { this.quantitaRichiesta = quantitaRichiesta; }
}
