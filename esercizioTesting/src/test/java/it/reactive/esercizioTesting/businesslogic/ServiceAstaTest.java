package it.reactive.esercizioTesting.businesslogic;

import it.reactive.esercizioTesting.exception.*;
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

    @Test(expected = PartecipanteNonCensitoException.class)
    public void testPartecipanteNonCensito() {
        serviceAsta.inizializza(Arrays.asList("GIOVANNI", "GIACOMO", "ALDO"));
        serviceAsta.rilancia("MARCO", 5);  // marco non presente nella lista
    }

    @Test
    public void rilancia(){
        serviceAsta.inizializza(Arrays.asList("GIOVANNI","GIACOMO","ALDO"));
        serviceAsta.setOggettoBandito("Auto");
        serviceAsta.rilancia("GIOVANNI",5);
        assertEquals("Errore nel rilancio",6,serviceAsta.getValoreSessioneAsta());
        serviceAsta.rilancia("GIACOMO",-2);
        assertEquals("Errore nel sovrascrivere il valore di sessione",6,serviceAsta.getValoreSessioneAsta());
    }

    @Test(expected = ValoreNonAmmessoException.class)
    public void testValoreNonAmmesso() {
        serviceAsta.inizializza(Arrays.asList("GIOVANNI", "GIACOMO", "ALDO"));
        serviceAsta.rilancia("GIOVANNI", 0);  // Valore non ammesso
    }

    @Test
    public void rilanciaProgressivo(){
        serviceAsta.inizializza(Arrays.asList("GIOVANNI","BRUNO"));
        serviceAsta.rilancia("GIOVANNI",5);
        serviceAsta.rilancia("BRUNO",5);
        serviceAsta.rilancia("GIOVANNI",5);
        assertEquals("errore nel rilancia progressivo", 16, serviceAsta.getValoreSessioneAsta());
    }

    @Test(expected = TurnoNonValidoException.class)
    public void testTurnoNonValido() {
        serviceAsta.inizializza(Arrays.asList("GIOVANNI", "GIACOMO", "ALDO"));
        serviceAsta.rilancia("GIOVANNI", 5);  // "GIOVANNI" è il primo, il suo turno deve passare a "GIACOMO"
        serviceAsta.rilancia("ALDO", 10);  // ALDO non è ancora il suo turno, quindi deve lanciare un'eccezione
    }


    @Test
    public void getValoreSessioneAstaAndVincitore() {
        serviceAsta.inizializza(Arrays.asList("GIOVANNI","GIACOMO","ALDO"));
        serviceAsta.rilancia("GIOVANNI",5);
        serviceAsta.rilancia("GIACOMO",4);
        System.out.println(serviceAsta.getVincitore());
        assertEquals("errore nel getSessioneAsta",10, serviceAsta.getValoreSessioneAsta());
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
        serviceAsta.inizializza(Arrays.asList("GIOVANNI","GIACOMO","ALDO"));
        serviceAsta.rilancia("GIOVANNI",5);
        serviceAsta.rilancia("GIACOMO",4);
        serviceAsta.addPartecipante("Giulio");  // eccezione perchè aggiungo un partecimente con asta in corso

    }

    @Test(expected = PartecipanteEsistenteException.class)
    public void addPartecipanteEsistente() {
        serviceAsta.addPartecipante("GIACOMO");
        serviceAsta.getPartecipanti();
        assertEquals("errore nell'add", Arrays.asList("Default","GIACOMO"),serviceAsta.getPartecipanti());
        serviceAsta.addPartecipante("GIACOMO");
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