package it.reactive.torneoDemo.DTO.torneo;

import javax.validation.constraints.NotNull;

public class TorneoDTO {

    @NotNull
    private String nomeTorneo;

    public String getNomeTorneo() {
        return nomeTorneo;
    }

    public void setNomeTorneo(String nomeTorneo) {
        this.nomeTorneo = nomeTorneo;
    }
}
