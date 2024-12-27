package it.reactive.torneoDemo.model;

import java.util.Set;

public class SquadraModel {
    private Integer idSquadra;
    private String nome;
    private String coloriSociali;
    private Set<GiocatoreModel> giocatori;
    private TifoseriaModel tifoseria;
    private Set<TorneoModel> tornei; // ---> LASCIARLO NEL MODEL

    public SquadraModel(Integer idSquadra, String nome, String coloriSociali, Set<GiocatoreModel> giocatori, TifoseriaModel tifoseria, Set<TorneoModel> tornei) {
        this.idSquadra = idSquadra;
        this.nome = nome;
        this.coloriSociali = coloriSociali;
        this.giocatori = giocatori;
        this.tifoseria = tifoseria;
        this.tornei = tornei;
    }

    public SquadraModel() {
    }

    public Integer getIdSquadra() {
        return idSquadra;
    }

    public void setIdSquadra(Integer idSquadra) {
        this.idSquadra = idSquadra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getColoriSociali() {
        return coloriSociali;
    }

    public void setColoriSociali(String coloriSociali) {
        this.coloriSociali = coloriSociali;
    }

    public Set<GiocatoreModel> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(Set<GiocatoreModel> giocatori) {
        this.giocatori = giocatori;
    }

    public TifoseriaModel getTifoseria() {
        return tifoseria;
    }

    public void setTifoseria(TifoseriaModel tifoseria) {
        this.tifoseria = tifoseria;
    }

    public Set<TorneoModel> getTornei() {
        return tornei;
    }

    public void setTornei(Set<TorneoModel> tornei) {
        this.tornei = tornei;
    }
}