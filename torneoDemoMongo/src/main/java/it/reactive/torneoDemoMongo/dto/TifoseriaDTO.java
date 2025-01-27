package it.reactive.torneoDemoMongo.dto;

import javax.validation.constraints.NotBlank;

public class TifoseriaDTO {

    @NotBlank
    private String nomeTifoseria;

    public String getNomeTifoseria() {
        return nomeTifoseria;
    }

    public void setNomeTifoseria(String nomeTifoseria) {
        this.nomeTifoseria = nomeTifoseria;
    }
}
