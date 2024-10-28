package academy.esercizi.esercizio_24;

import java.util.Random;
import java.util.Scanner;

public class Esercizio_24_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Generazione del mucchietto: tra 10 e 100.
        int numeroBiglieTotali = random.nextInt(91) + 10;

        // Decide chi inizia: 0 per il giocatore, 1 per il computer.
        boolean turnoGiocatore = random.nextInt(2) == 0; // true: giocatore - false: pc

        // Decide se il computer giocherà in modo intelligente o stupido.
        boolean modalitaGiocoPc = random.nextInt(2) == 0; // true: "modalità stupida" - false: "modalità intelligente"

        System.out.println("Gioco di Nim");
        System.out.println("--------");
        System.out.println("Biglie iniziali: " + numeroBiglieTotali);

        while (numeroBiglieTotali > 1) {
            if (turnoGiocatore) { // turno utente
                System.out.println("Quante biglie vuoi prelevare? (1 - " + numeroBiglieTotali / 2 + ")");
                int bigliePrelevate = scanner.nextInt(); // numero valido: compreso tra 1 e n/2
                if (bigliePrelevate >= 1 && bigliePrelevate <= numeroBiglieTotali / 2) {
                    numeroBiglieTotali -= bigliePrelevate;
                    System.out.println("Hai preso " + bigliePrelevate + " biglie. Rimangono " + numeroBiglieTotali + " biglie.");
                    turnoGiocatore = false; // cambia turno
                } else {
                    System.out.println("Numero non valido. Riprova.");
                }
            } else { // turno PC
                int bigliePrelevate;
                if (modalitaGiocoPc) { // modalità stupida
                    bigliePrelevate = random.nextInt(Math.min(numeroBiglieTotali / 2, numeroBiglieTotali - 1)) + 1;
                } else { // modalità intelligente
                    bigliePrelevate = modalitaIntelligente(numeroBiglieTotali);
                    if (bigliePrelevate == -1 || (numeroBiglieTotali == 3 || numeroBiglieTotali == 7 || numeroBiglieTotali == 15 || numeroBiglieTotali == 31 || numeroBiglieTotali == 63)) {
                        bigliePrelevate = random.nextInt(Math.min(numeroBiglieTotali / 2, numeroBiglieTotali - 1)) + 1;
                    }
                }
                numeroBiglieTotali -= bigliePrelevate;
                System.out.println("Il computer ha preso " + bigliePrelevate + " biglie. Rimangono " + numeroBiglieTotali + " biglie.");
                turnoGiocatore = true; // cambia turno
            }
        }

        // Vincitore
        if (!turnoGiocatore) {
            System.out.println("Il computer ha preso l'ultima biglia. Hai vinto!");
        } else {
            System.out.println("Hai preso l'ultima biglia. Il computer ha vinto!");
        }
    }

    private static int modalitaIntelligente(int biglieTotali) {
        int[] potenzeMeno1 = {3, 7, 15, 31, 63};

        for (int dimensioni : potenzeMeno1) {
            if (biglieTotali > dimensioni) {
                return biglieTotali - dimensioni; // restituisce il numero di biglie da prendere
            }
        }
        return -1; // impossibilità di effettuare mossa intelligente
    }
}
