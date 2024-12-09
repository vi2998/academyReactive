package it.reactive.torneoDemo.DTO.squadra;

import it.reactive.torneoDemo.resource.Giocatore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SquadraGiocatoreDTO {

    @NotNull
    @Size(min = 3)
    private String nome;
    private String coloreSociale;
    private List<Giocatore> gicatori;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getColoreSociale() {
        return coloreSociale;
    }

    public void setColoreSociale(String coloreSociale) {
        this.coloreSociale = coloreSociale;
    }

    public List<Giocatore> getGicatori() {
        return gicatori;
    }

    public void setGicatori(List<Giocatore> gicatori) {
        this.gicatori = gicatori;
    }
}
