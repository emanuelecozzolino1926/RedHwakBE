package emanueleCozzolino.redhawkbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prodotti")
public class Prodotto {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nome;

    private String descrizione;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    private String immagineUrl;

    @Column(nullable = false)
    private boolean disponibile = true;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties("prodotti")
    private Categoria categoria;

    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("prodotto")
    private List<ProdottoIngrediente> ricetta = new ArrayList<>();

    public Prodotto() {}

    public Prodotto(String nome, String descrizione, BigDecimal prezzo, String immagineUrl, Categoria categoria) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.immagineUrl = immagineUrl;
        this.categoria = categoria;
    }

    public UUID getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }

    public String getImmagineUrl() { return immagineUrl; }
    public void setImmagineUrl(String immagineUrl) { this.immagineUrl = immagineUrl; }

    public boolean isDisponibile() { return disponibile; }
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public List<ProdottoIngrediente> getRicetta() { return ricetta; }
    public void setRicetta(List<ProdottoIngrediente> ricetta) { this.ricetta = ricetta; }
}
