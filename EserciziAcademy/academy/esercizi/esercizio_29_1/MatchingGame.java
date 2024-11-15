package academy.esercizi.esercizio_29_1;

import java.util.Random;

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
    private Grid[][] griglia = new Grid[DIMENSIONE_GRIGLIA][DIMENSIONE_GRIGLIA];
    private static int punteggio = 0;


    public void gioca() {
        popolaGriglia();
    }

    private void popolaGriglia() {
        // array di carte doppie
        Tile[] carteDisponibili = new Tile[DIMENSIONE_GRIGLIA * DIMENSIONE_GRIGLIA];
        int indiceCarteArray = 0;

        // Aggiungiamo le carte due volte (per formare le coppie)
        for (int i = 0; i < Carte.values().length; i++) {
            carteDisponibili[indiceCarteArray] = new Tile(i);
            indiceCarteArray++;
            carteDisponibili[indiceCarteArray] = new Tile(i);
            indiceCarteArray++;
        }

        // Mescoliamo l'array di carte
        Random random = new Random();
        for (int i = 0; i < carteDisponibili.length; i++) {
            int randomIndex = random.nextInt(carteDisponibili.length);
            Tile temp = carteDisponibili[i];
            carteDisponibili[i] = carteDisponibili[randomIndex];
            carteDisponibili[randomIndex] = temp;
        }

        indiceCarteArray = 0;  // Reset dell'indice per l'array delle carte
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                Location location = new Location(i, j);
                griglia[i][j] = new Grid(carteDisponibili[indiceCarteArray], location);
                indiceCarteArray++;
            }
        }
        stampaMemory();
    }

    private void stampaMemory() {
        for (int i = 0; i < griglia.length; i++) {
            for (int j = 0; j < griglia[i].length; j++) {
                Grid cella = griglia[i][j];
                Tile carta = cella.getCarta();

                if (carta.getGirata()) {
                    System.out.print(carta.getValoreCarta() + " ");
                } else {
                    System.out.print("\u2580 ");
                }
            }
            System.out.println();
        }
    }


}

