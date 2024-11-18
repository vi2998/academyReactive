package academy.esercizi.esercizio_29_1;

import java.util.Random;
import java.util.Scanner;

public class MatchingGame {
    /* In un gioco di carte in cui si cercano le coppie,
       le carte disposte in una griglia con righe e colonne.
       Il giocatore scopre due carte per volta e, se sono uguali, guadagna un punto + 1 aggiuntivo se sono adiacenti
       Progettate un programma che consenta di giocare, usando le classi:
       Tile (carta),
       Location (posizione, che incapsula l’indicazione di una riga e di una colonna),
       Grid  (griglia) e
       MatchingGame (il gioco).
       Eseguire il gioco richiedendo tramite la classe Scanner ai giocatori le coordinate delle carte da mostrare.
       Il gioco finisce quando tutte le coppie sono state rivelate.
       */

    private final int DIMENSIONE_GRIGLIA = 4;
    private final Grid[][] griglia = new Grid[DIMENSIONE_GRIGLIA][DIMENSIONE_GRIGLIA];
    private int punteggio = 0;


    public void gioca() {
        popolaGriglia();
        stampaMemory();

        boolean checkCoppie = false;
        Scanner scanner = new Scanner(System.in);

        while (!checkCoppie) {
            System.out.println("Inserisci le coordinate della prima carta:");
            Location carta1 = chiediCoordinate(scanner);

            System.out.println("Inserisci le coordinate della seconda carta:");
            Location carta2 = chiediCoordinate(scanner);

            Tile t1 = griglia[carta1.getRiga()][carta1.getColonna()].getCarta();
            Tile t2 = griglia[carta2.getRiga()][carta2.getColonna()].getCarta();

            if (t1.getValoreCarta() == t2.getValoreCarta()) {
                t1.rivelaCarta();
                t2.rivelaCarta();
                System.out.println("Coppia trovata");
                punteggio++;
            } else {
                System.out.println("Le carte non sono uguali");
            }
            stampaMemory();
            checkCoppie = checkCoppie();
            System.out.println("griglia con carte scoperte");
            stampaCarteGirate();
            System.out.println("--------------------");
        }
        System.out.println("Gioco finito! Il tuo punteggio finale è: " + punteggio);
        scanner.close();
    }

    private void popolaGriglia() {
        int numCarte = DIMENSIONE_GRIGLIA * DIMENSIONE_GRIGLIA;

        Tile[] carteDisponibili = new Tile[numCarte];

        //creo coppie
        for (int i = 0; i < numCarte / 2; i++) {
            carteDisponibili[2 * i] = new Tile(i);
            carteDisponibili[2 * i + 1] = new Tile(i);
        }

        //mischio carte
        Random random = new Random();
        for (int i = 0; i < carteDisponibili.length; i++) {
            int randomIndex = random.nextInt(carteDisponibili.length);
            Tile temp = carteDisponibili[i];
            carteDisponibili[i] = carteDisponibili[randomIndex];
            carteDisponibili[randomIndex] = temp;
        }

        //popolo griglia
        int indiceCarte = 0;
        for (int i = 0; i < DIMENSIONE_GRIGLIA; i++) {
            for (int j = 0; j < DIMENSIONE_GRIGLIA; j++) {
                Location location = new Location(i, j);
                griglia[i][j] = new Grid(carteDisponibili[indiceCarte], location);
                indiceCarte++;
            }
        }
    }

    private void stampaMemory() {
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                Grid cella = griglia[i][j];
                Tile carta = cella.getCarta();

                if (carta.isGirata()) {
                    System.out.print(carta.getValoreCarta() + " ");
                } else {
                    System.out.print("▀ ");
                }
            }
            System.out.println();
        }
    }

    private Location chiediCoordinate(Scanner scanner) {
        int riga;
        int colonna;

        System.out.print("Inserisci la riga (0-" + (DIMENSIONE_GRIGLIA - 1) + "): ");
        riga = scanner.nextInt();
        System.out.print("Inserisci la colonna (0-" + (DIMENSIONE_GRIGLIA - 1) + "): ");
        colonna = scanner.nextInt();
        if (riga < 0 || riga >= DIMENSIONE_GRIGLIA || colonna < 0 || colonna >= DIMENSIONE_GRIGLIA) {
            System.out.println("Coordinate non valide");
        }
        return new Location(riga, colonna);
    }

    private boolean checkCoppie() {
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                if (!griglia[i][j].getCarta().isGirata()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void stampaCarteGirate() {
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                Grid cella = griglia[i][j];
                Tile carta = cella.getCarta();
                System.out.print(carta.getValoreCarta() + " ");
            }
            System.out.println();
        }
    }
}