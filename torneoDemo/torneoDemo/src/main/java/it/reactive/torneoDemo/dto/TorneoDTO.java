package it.reactive.torneoDemo.dto;

import javax.validation.constraints.NotNull;

public class TorneoDTO {
    @NotNull
    String nomeTorneo;

    public @NotNull String getNomeTorneo() {
        return nomeTorneo;
    }

    public void setNomeTorneo(@NotNull String nomeTorneo) {
        this.nomeTorneo = nomeTorneo;
    }
}
