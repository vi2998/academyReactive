package it.reactive.torneoDemo.dto;

import javax.validation.constraints.NotNull;

public class TifoseriaDTO {

    @NotNull
    String nomeTifoseria;

    public @NotNull String getNomeTifoseria() {
        return nomeTifoseria;
    }

    public void setNomeTifoseria(@NotNull String nomeTifoseria) {
        this.nomeTifoseria = nomeTifoseria;
    }
}
