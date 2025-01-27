package it.reactive.torneoDemoMongo.resource;

import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;

public class GiocatoreResponse {
    private ObjectId idGiocatore;
    private String nomeCognome;
    private Integer numeroAmmonizioni;
    private Set<Trasferimenti> trasferimenti = new HashSet<>();

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
}

