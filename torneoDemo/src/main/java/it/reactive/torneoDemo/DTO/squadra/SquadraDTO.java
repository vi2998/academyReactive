package it.reactive.torneoDemo.DTO.squadra;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SquadraDTO {

    @NotNull
    @Size(min = 3, message = "Il nome della squadra deve essere almeno di 3 caratteri")
    private String nome;
    private String coloreSociale;


    public SquadraDTO() {
        this.nome = nome;
        this.coloreSociale = coloreSociale;
    }

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
}
