package it.reactive.torneoDemoMongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "squadre")
public class SquadraModel {
    @Id
    private ObjectId idSquadra;
    private String nome;
    private String coloriSociali;
    private Set<GiocatoreModel> giocatori;
    private TifoseriaModel tifoseria;
    private Set<TorneoModel> tornei;

    public SquadraModel(ObjectId idSquadra, String nome, String coloriSociali, Set<GiocatoreModel> giocatori, TifoseriaModel tifoseria, Set<TorneoModel> tornei) {
        this.idSquadra = idSquadra;
        this.nome = nome;
        this.coloriSociali = coloriSociali;
        this.giocatori = giocatori;
        this.tifoseria = tifoseria;
        this.tornei = tornei;
    }

    public SquadraModel() {
    }

    public ObjectId getIdSquadra() {
        return idSquadra;
    }

    public void setIdSquadra(ObjectId idSquadra) {
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