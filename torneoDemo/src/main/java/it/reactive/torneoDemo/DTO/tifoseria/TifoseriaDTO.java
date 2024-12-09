package it.reactive.torneoDemo.DTO.tifoseria;

import javax.validation.constraints.NotNull;

public class TifoseriaDTO {

    @NotNull
    private String nome;

    public @NotNull String getNome() {
        return nome;
    }

    public void setNome(@NotNull String nome) {
        this.nome = nome;
    }
}
