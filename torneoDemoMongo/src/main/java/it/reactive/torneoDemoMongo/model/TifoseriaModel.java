package it.reactive.torneoDemoMongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TifoseriaModel {
    @Id
    private ObjectId idTifoseria;
    private String nomeTifoseria;
    private SquadraModel squadra; // ---> LASCIARLO NEL MODEL

    public TifoseriaModel(ObjectId idTifoseria, String nomeTifoseria, SquadraModel squadra) {
        this.idTifoseria = idTifoseria;
        this.nomeTifoseria = nomeTifoseria;
        this.squadra = squadra;
    }

    public TifoseriaModel() {
    }

    public ObjectId getIdTifoseria() {
        return idTifoseria;
    }

    public void setIdTifoseria(ObjectId idTifoseria) {
        this.idTifoseria = idTifoseria;
    }

    public String getNomeTifoseria() {
        return nomeTifoseria;
    }

    public void setNomeTifoseria(String nomeTifoseria) {
        this.nomeTifoseria = nomeTifoseria;
    }

    public SquadraModel getSquadra() {
        return squadra;
    }

    public void setSquadra(SquadraModel squadra) {
        this.squadra = squadra;
    }
}
