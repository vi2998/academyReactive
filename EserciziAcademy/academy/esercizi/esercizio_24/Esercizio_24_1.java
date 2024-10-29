package academy.esercizi.esercizio_24;

import java.util.Random;
import java.util.Scanner;

public class Esercizio_24_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Generazione del mucchietto: tra 10 e 100.
        int numeroBiglieTotali = random.nextInt(91) + 10;

        // Decide chi inizia: PC o UTENTE.
        Turno turnoGiocatore = random.nextInt(2) == 0 ? Turno.UTENTE : Turno.PC;

        // Decide la difficoltà del computer: STUPIDA o INTELLIGENTE.
        DifficoltaPc difficoltaPc = random.nextInt(2) == 0 ? DifficoltaPc.STUPIDA : DifficoltaPc.INTELLIGENTE;

        System.out.println("Gioco di Nim");
        System.out.println("--------");
        System.out.println("Biglie iniziali: " + numeroBiglieTotali);

        do {
            if (turnoGiocatore == Turno.UTENTE) { // turno utente
                System.out.println("Quante biglie vuoi prelevare? (1 - " + numeroBiglieTotali / 2 + ")");
                int bigliePrelevate = scanner.nextInt();
                if (bigliePrelevate >= 1 && bigliePrelevate <= numeroBiglieTotali / 2) { // numero valido: compreso tra 1 e n/2
                    numeroBiglieTotali -= bigliePrelevate;
                    System.out.println("Hai preso " + bigliePrelevate + " biglie. Rimangono " + numeroBiglieTotali + " biglie.");
                    turnoGiocatore = Turno.PC; // cambia turno
                } else {
                    System.out.println("Numero non valido. Riprova.");
                }
            } else { // turno PC
                int bigliePrelevate;
                if (difficoltaPc == DifficoltaPc.STUPIDA) { // modalità stupida
                    bigliePrelevate = modalitaStupida(numeroBiglieTotali);
                } else { // modalità intelligente
                    bigliePrelevate = modalitaIntelligente(numeroBiglieTotali);
                    // Se non ci sono mosse intelligenti, gioca in modo stupido
                    if (bigliePrelevate == -1) {
                        bigliePrelevate = modalitaStupida(numeroBiglieTotali);
                    }
                }
                numeroBiglieTotali -= bigliePrelevate;
                System.out.println("Il computer ha preso " + bigliePrelevate + " biglie. Rimangono " + numeroBiglieTotali + " biglie.");
                turnoGiocatore = Turno.UTENTE; // cambia turno
            }
        } while (numeroBiglieTotali > 1);

        // Vincitore
        if (turnoGiocatore == Turno.PC) {
            System.out.println("Il computer ha preso l'ultima biglia. Hai vinto!");
        } else {
            System.out.println("Hai preso l'ultima biglia. Il computer ha vinto!");
        }
    }

    private static int modalitaStupida(int biglieTotali) {
        // Limite massimo per le biglie che possono essere prelevate. (biglieTotali - 1) per lasciare almeno 1 biglia da prelevare.
        // Uso il .min che assicura che non si possa prelevar più della metà delle biglie e che ci sia sempre almeno una biglia rimanente.
        int massimoPrelevabile = Math.min(biglieTotali / 2, biglieTotali - 1);
        int bigliePrelevate = new Random().nextInt(massimoPrelevabile) + 1; // numero tra 1 e il massimo prelevabile
        return bigliePrelevate;
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
