package it.reactive.torneoDemo.model;

import it.reactive.torneoDemo.resource.Trasferimenti;

import java.util.Set;

public class GiocatoreModel {
    private Integer idGiocatore;
    private String nomeCognome;
    private Integer numeroAmmonizioni;
    private Set<Trasferimenti> trasferimenti; // non presente sul DB e sulla nostra applicazione (recupero storico tramite REST API)
    private SquadraModel squadra; // LASCIARLO NEL MODEL

    public GiocatoreModel(Integer idGiocatore, String nomeCognome, Integer numeroAmmonizioni, Set<Trasferimenti> trasferimenti, SquadraModel squadra) {
        this.idGiocatore = idGiocatore;
        this.nomeCognome = nomeCognome;
        this.numeroAmmonizioni = numeroAmmonizioni;
        this.trasferimenti = trasferimenti;
        this.squadra = squadra;
    }

    public GiocatoreModel() {
    }

    public Integer getIdGiocatore() {
        return idGiocatore;
    }

    public void setIdGiocatore(Integer idGiocatore) {
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