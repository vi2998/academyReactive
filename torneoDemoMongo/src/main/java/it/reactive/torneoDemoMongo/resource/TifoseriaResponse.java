package it.reactive.torneoDemoMongo.resource;

import org.bson.types.ObjectId;

public class TifoseriaResponse {
    private ObjectId idTifoseria;
    private String nomeTifoseria;


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

}

