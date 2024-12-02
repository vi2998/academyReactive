package it.reactive.esercizioTesting.businesslogic;

import it.reactive.esercizioTesting.entrypoint.Asta;
import it.reactive.esercizioTesting.exception.AstaInCorsoException;
import it.reactive.esercizioTesting.exception.AstaTerminataException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test(expected = AstaTerminataException.class)
    public void rilanciaAstaTerminataException() {
        serviceAsta.inizializza(Arrays.asList("Giuseppe"));
        serviceAsta.rilancia("Giuseppe", 5);
    }

    @Test
    public void rilancia(){
        serviceAsta.inizializza(Arrays.asList("GIOVANNI","GIACOMO","ALDO"));
        serviceAsta.rilancia("GIOVANNI",5);
        assertEquals("Errore nel rilancio",6,serviceAsta.getValoreSessioneAsta());
        serviceAsta.rilancia("GIACOMO",-2);
        assertEquals("Erroe nel sovrascrivere il valore di sessione",6,serviceAsta.getValoreSessioneAsta());
    }

    @Test
    public void getValoreSessioneAstaAndVincitore() {
        serviceAsta.inizializza(Arrays.asList("GIOVANNI","GIACOMO","ALDO"));
        serviceAsta.rilancia("GIOVANNI",5);
        serviceAsta.rilancia("GIACOMO",4);
        System.out.println(serviceAsta.getVincitore());
        assertEquals("errore neò getSessioneAsta",10, serviceAsta.getValoreSessioneAsta());
        assertEquals("errore nel getVinvitore","GIACOMO",serviceAsta.getVincitore());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setPartecipanti() {
        List<String> partecipanti = new ArrayList<>();
        partecipanti.add("Federico");
        partecipanti.add("Giovanni");
        serviceAsta.setPartecipanti(partecipanti);
    }

    @Test(expected = AstaInCorsoException.class)
    public void addPartecipante() {

    }

    @Test()
    public void getPartecipanti() {
        List<String> partecipanti = new ArrayList<>();
        serviceAsta.addPartecipante("Giacomo"); // va in posizione 1 della lista della serviceAsta
        partecipanti.add("Default"); // va in posizione 0 di lista partecipanti
        partecipanti.add("Giacomo"); // va in posizione 1 di lista partecipanti
        assertEquals("Errore nel metodo getPartecipanti", partecipanti, serviceAsta.getPartecipanti());
        assertEquals("Errore nel metodo add", "Giacomo", serviceAsta.getPartecipanti().get(1));
    }

    @Test
    public void fine() {
    }

    @Test
    public void verificaFineAsta() {
    }
}