package it.reactive.torneoDemoMongo.resource;

import org.bson.types.ObjectId;

import java.util.Set;

public class TorneoResponse {
    private ObjectId idTorneo;
    String nomeTorneo;
    Set<SquadraResponse> squadre;

    public ObjectId getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(ObjectId idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNomeTorneo() {
        return nomeTorneo;
    }

    public void setNomeTorneo(String nomeTorneo) {
        this.nomeTorneo = nomeTorneo;
    }

    public Set<SquadraResponse> getSquadre() {
        return squadre;
    }

    public void setSquadre(Set<SquadraResponse> squadre) {
        this.squadre = squadre;
    }
}
