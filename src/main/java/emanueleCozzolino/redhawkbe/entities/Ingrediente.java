package emanueleCozzolino.redhawkbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingredienti")
public class Ingrediente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private int giacenza;

    @Version
    private long version;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("ingrediente")
    private List<ProdottoIngrediente> ricette = new ArrayList<>();

    public Ingrediente() {}

    public Ingrediente(String nome, int giacenza) {
        this.nome = nome;
        this.giacenza = giacenza;
    }

    public UUID getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getGiacenza() { return giacenza; }
    public void setGiacenza(int giacenza) { this.giacenza = giacenza; }

    public long getVersion() { return version; }

    public List<ProdottoIngrediente> getRicette() { return ricette; }
    public void setRicette(List<ProdottoIngrediente> ricette) { this.ricette = ricette; }
}
