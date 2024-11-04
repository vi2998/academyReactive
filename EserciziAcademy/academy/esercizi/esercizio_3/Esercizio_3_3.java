package academy.esercizi.esercizio_3;

import java.util.Random;

public class Esercizio_3_3 {
    /* Create un array bidimensionale che rappresenti la scacchiera del «gioco del tris»: deve avere tre righe e tre colonne e
    ciascuna casella può contenere le stringhe «x», «o» oppure « ».
    Scrivere un metodo di assegnazione che inserisca una «x» nell’angolo superiore destro della scacchiera,
    valorizzare le altre posizioni in maniera casuale. */
    public static void main(String[] args) {
        String[][] scacchieraTris = new String[3][3];
        assegnaX(scacchieraTris);
        randomizzaScacchiera(scacchieraTris);
        stampaScacchiera(scacchieraTris);

        // stampo i caratteri sulla diagonale richiesti:
        System.out.println("La diagonale da cella [0][0] a [2][2] è:");
        System.out.printf("%s - ", scacchieraTris[0][0]);
        System.out.printf("%s - ", scacchieraTris[1][1]);
        System.out.printf("%s", scacchieraTris[2][2]);

    }


    public static void assegnaX(String[][] scacchiera) {
        scacchiera[0][2] = "x";
    }

    public static void randomizzaScacchiera(String[][] scacchiera) {
        Random random = new Random();
        String[] valoriPossibili = {"x", "o", "/"};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Assegna un valore casuale solo se la casella è ancora vuota
                if (scacchiera[i][j] == null) {
                    scacchiera[i][j] = valoriPossibili[random.nextInt(valoriPossibili.length)];
                }
            }
        }
    }

    public static void stampaScacchiera(String[][] scacchiera) {
        for (int i = 0; i < scacchiera.length; i++) {
            for (int j = 0; j < scacchiera[i].length; j++) {
                System.out.print(scacchiera[i][j] + " ");
            }
            System.out.println();
        }
    }
}