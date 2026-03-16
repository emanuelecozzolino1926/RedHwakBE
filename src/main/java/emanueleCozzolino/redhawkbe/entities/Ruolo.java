package emanueleCozzolino.redhawkbe.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ruoli")
public class Ruolo {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nomeRuolo;

    public Ruolo() {}

    public Ruolo(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeRuolo() {
        return nomeRuolo;
    }

    public void setNomeRuolo(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }
}
