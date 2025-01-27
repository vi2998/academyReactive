package it.reactive.torneoDemoMongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "torneo")
public class TorneoModel {
    private ObjectId idTorneo;
    private String nomeTorneo;
    private Set<SquadraModel> squadre;

    public TorneoModel(ObjectId idTorneo, String nomeTorneo, Set<SquadraModel> squadre) {
        this.idTorneo = idTorneo;
        this.nomeTorneo = nomeTorneo;
        this.squadre = squadre;
    }

    public TorneoModel() {
    }

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

    public Set<SquadraModel> getSquadre() {
        return squadre;
    }

    public void setSquadre(Set<SquadraModel> squadre) {
        this.squadre = squadre;
    }
}
