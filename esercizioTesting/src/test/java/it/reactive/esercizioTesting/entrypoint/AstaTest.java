package it.reactive.esercizioTesting.entrypoint;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import it.reactive.esercizioTesting.businesslogic.ServiceAsta;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AstaTest {

    @InjectMocks
    Asta asta;
    @Mock
    ServiceAsta serviceAsta;

    @Test
    public void testAvviaEFineAstaTrue() {
        List<String> partecipanti = Arrays.asList("Giacomo", "Giuseppe");
        String oggettoBandito = "Auto";
        when(serviceAsta.verificaFineAsta()).thenReturn(true);
        asta.avvia("Auto", partecipanti);
        verify(serviceAsta, times(1)).inizializza(partecipanti);
        verify(serviceAsta, times(1)).setOggettoBandito(oggettoBandito);
        verify(serviceAsta, times(1)).fine();
    }

    @Test
    public void testAvviaEFineAstaFalse() {
        List<String> partecipanti = Arrays.asList("Giacomo", "Giuseppe");
        String oggettoBandito = "Auto";
        when(serviceAsta.verificaFineAsta()).thenReturn(false);
        asta.avvia("Auto", partecipanti);
        verify(serviceAsta, times(1)).inizializza(partecipanti);
        verify(serviceAsta, times(1)).setOggettoBandito(oggettoBandito);
    }

    @Test
    public void testRilancia() {
        asta.rilancia("Federico", 1000);
        verify(serviceAsta, times(1)).rilancia("Federico", 1000);
        when(serviceAsta.getValoreSessioneAsta()).thenReturn(5);
        assertEquals(asta.rilancia("Federico", 1000), 5);
    }

    @Test
    public void testPassa() {
        String nomeBanditore = "Artu";
        asta.passa(nomeBanditore);
        verify(serviceAsta, times(1)).rilancia(nomeBanditore, -1);
        when(serviceAsta.verificaFineAsta()).thenReturn(true);
        assertTrue(asta.passa(nomeBanditore));
    }

    @Test
    public void verificaFineAsta() {
        asta.verificaFineAsta();
        verify(serviceAsta, times(1)).verificaFineAsta();
        when(serviceAsta.verificaFineAsta()).thenReturn(true);
        assertTrue(asta.verificaFineAsta());
    }

    @Test
    public void getValoreCorrente() {
        when(serviceAsta.getValoreSessioneAsta()).thenReturn(5);
        assertEquals(asta.getValoreCorrente(), 5);
    }

    @Test
    public void addPartecipante() {
        List<String> partecipanti = Arrays.asList("Fido");
        asta.addPartecipante("Fido");
        verify(serviceAsta, times(1)).addPartecipante("Fido");
    }


    @Test
    public void visualizzaPartecipantiSessioneCorrente() {
        List<String> partecipantiService = Arrays.asList("Federico", "Bob", "Vito");
        when(serviceAsta.getPartecipanti()).thenReturn(partecipantiService);

        List<String> partecipanti = asta.visualizzaPartecipantiSessioneCorrente();
        assertEquals(partecipantiService, partecipanti);
    }

    @Test
    public void fineAstaForzata() {
		List<String> partecipantiService = Arrays.asList("Federico", "Bob", "Vito");
		serviceAsta.inizializza(partecipantiService);
		asta.fineAstaForzata();
		verify(serviceAsta,times(1)).fine();
    }

    @Test
    public void testSetPartecipanti() {
        List<String> partecipanti = Arrays.asList("Giacomo", "Giuseppe");
        asta.setPartecipanti(partecipanti);
        verify(serviceAsta, times(1)).setPartecipanti(partecipanti);
        when(serviceAsta.setPartecipanti(partecipanti)).thenReturn(partecipanti);
        assertEquals(partecipanti, asta.setPartecipanti(partecipanti));
    }
}
