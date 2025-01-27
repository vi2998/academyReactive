package it.reactive.torneoDemoMongo.model;

import it.reactive.torneoDemoMongo.resource.Trasferimenti;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class GiocatoreModel {
    @Id
    private ObjectId idGiocatore;
    private String nomeCognome;
    private Integer numeroAmmonizioni;
    private Set<Trasferimenti> trasferimenti; // non presente sul DB e sulla nostra applicazione (recupero storico tramite REST API)
    private SquadraModel squadra; // LASCIARLO NEL MODEL

    public GiocatoreModel(ObjectId idGiocatore, String nomeCognome, Integer numeroAmmonizioni, Set<Trasferimenti> trasferimenti, SquadraModel squadra) {
        this.idGiocatore = idGiocatore;
        this.nomeCognome = nomeCognome;
        this.numeroAmmonizioni = numeroAmmonizioni;
        this.trasferimenti = trasferimenti;
        this.squadra = squadra;
    }

    public GiocatoreModel() {
    }

    public ObjectId getIdGiocatore() {
        return idGiocatore;
    }

    public void setIdGiocatore(ObjectId idGiocatore) {
        this.idGiocatore = idGiocatore;
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public void setNomeCognome(String nomeCognome) {
        this.nomeCognome = nomeCognome;
    }

    public Integer getNumeroAmmonizioni() {
        return numeroAmmonizioni;
    }

    public void setNumeroAmmonizioni(Integer numeroAmmonizioni) {
        this.numeroAmmonizioni = numeroAmmonizioni;
    }

    public Set<Trasferimenti> getTrasferimenti() {
        return trasferimenti;
    }

    public void setTrasferimenti(Set<Trasferimenti> trasferimenti) {
        this.trasferimenti = trasferimenti;
    }

    public SquadraModel getSquadra() {
        return squadra;
    }

    public void setSquadra(SquadraModel squadra) {
        this.squadra = squadra;
    }
}