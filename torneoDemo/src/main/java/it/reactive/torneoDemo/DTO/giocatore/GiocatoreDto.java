package it.reactive.torneoDemo.DTO.giocatore;


import javax.validation.constraints.NotNull;

public class GiocatoreDto {

    @NotNull(message = "Il nome non puo essere null")
    private String nome;

    @NotNull(message = "Il nome non puo essere null")
    private String cognome;

    public String getNomeCognome() {
        return nome;
    }

    public void setNomeCognome(String nome) {
        this.nome = nome;
    }
}
