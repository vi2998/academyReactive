package it.reactive.esercizioTesting.businesslogic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class ServiceAstaTest {

    ServiceAsta serviceAsta;


    @Before
    public void creaOggetto(){
        serviceAsta = new ServiceAsta();
    }

    @Test
    public void costruttore() {
        serviceAsta = new ServiceAsta();
        assertEquals("Partecipanti di default errati" ,
                "Default", serviceAsta.getPartecipanti().get(0));
    }

    @Test
    public void inizializza() {
        List<String> partecipanti = new ArrayList<>();
        partecipanti.add("Federico");
        partecipanti.add("Gabriele");
        partecipanti.add("Simona");

        serviceAsta.inizializza(partecipanti);

        assertEquals("Inizializzazione non corretta", partecipanti, serviceAsta.getPartecipanti());

    }

    @Test
    public void setOggettoBandito() {
    }

    @Test
    public void rilancia() {
    }

    @Test
    public void getValoreSessioneAsta() {
    }

    @Test
    public void getVincitore() {
    }

    @Test
    public void setPartecipanti() {
    }

    @Test
    public void addPartecipante() {
    }

    @Test
    public void getPartecipanti() {
    }

    @Test
    public void fine() {
    }

    @Test
    public void verificaFineAsta() {
    }
}