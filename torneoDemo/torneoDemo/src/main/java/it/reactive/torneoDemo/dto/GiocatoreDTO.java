package it.reactive.torneoDemo.dto;

import javax.validation.constraints.NotNull;

public class GiocatoreDTO {
    @NotNull
    String nomeCognome;

    public @NotNull String getNomeCognome() {
        return nomeCognome;
    }

    public void setNomeCognome(@NotNull String nomeCognome) {
        this.nomeCognome = nomeCognome;
    }
}
