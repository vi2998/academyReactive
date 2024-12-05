package it.reactive.torneoDemo.dto;

import it.reactive.torneoDemo.resource.Giocatore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class SquadraGiocatoreDTO {
    @NotNull
    @Min(value = 3, message = "Il nome deve essere almeno di tre caratteri")
    String nome;
    String coloriSociali;
    Set<Giocatore> giocatori;

    public @NotNull @Min(value = 3, message = "Il nome deve essere almeno di tre caratteri") String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Min(value = 3, message = "Il nome deve essere almeno di tre caratteri") String nome) {
        this.nome = nome;
    }

    public String getColoriSociali() {
        return coloriSociali;
    }

    public void setColoriSociali(String coloriSociali) {
        this.coloriSociali = coloriSociali;
    }

    public Set<Giocatore> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(Set<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }
}
