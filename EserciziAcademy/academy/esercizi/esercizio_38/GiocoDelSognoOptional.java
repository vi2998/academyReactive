package academy.esercizi.esercizio_38;

import java.util.Optional;
import java.util.Scanner;

public class GiocoDelSognoOptional {
    /* TODO logica del gioco


        Il gioco termina nei seguenti casi:
        - il giocatore indovina
        - il numero da indovinare diventa 1
        - il giocatore tenta null dopo la terza mossa ma è valorizzato
        Se il giocatore tenta null nelle prime tre mosse ed è valorizzato perde solamente la mossa e può continuare a giocare.
  */

    public void gioca() {
        System.out.println("Il gioco inizia");
        ElementoCasualeOptional elementoCasualeOptional = new ElementoCasualeOptional();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Indovina se il valore è null oppure è valorizzato con un intero");
        int valoreUtente = scanner.nextInt();
        // Se sbaglia ed il numero non era NULL viene dimezzato (troncandolo all’intero più piccolo).
        if (!elementoCasualeOptional.getValore().equals(valoreUtente) && elementoCasualeOptional.getValore().isPresent()) {
            elementoCasualeOptional.setValore(Optional.of(elementoCasualeOptional.getValore().get() / 2));
        }

    }
}
