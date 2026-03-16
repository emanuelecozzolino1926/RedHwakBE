package emanueleCozzolino.redhawkbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categorie")
public class Categoria {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descrizione;

    private String immagineUrl;


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("categoria")
    private List<Prodotto> prodotti = new ArrayList<>();

    public Categoria() {}

    public Categoria(String nome, String descrizione, String immagineUrl) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagineUrl = immagineUrl;
    }

    public UUID getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getImmagineUrl() { return immagineUrl; }
    public void setImmagineUrl(String immagineUrl) { this.immagineUrl = immagineUrl; }

    public List<Prodotto> getProdotti() { return prodotti; }
    public void setProdotti(List<Prodotto> prodotti) { this.prodotti = prodotti; }
}
