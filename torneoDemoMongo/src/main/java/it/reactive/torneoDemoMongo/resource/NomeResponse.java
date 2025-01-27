package it.reactive.torneoDemoMongo.resource;

// aggiunta della responce per una get del nome

public class NomeResponse {

    private String nome;

    public NomeResponse(String nome) {
        this.nome = nome;
    }

    public NomeResponse() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}