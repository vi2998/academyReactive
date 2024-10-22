package academy.esercizi.esercizio_3;

import java.util.Scanner;

public class Esercizio_3_1 {
    public static void main(String[] args) {
        int[][] tabellaTombola = new int[10][9]; // 10 righe e 9 colonne

        // Popolazione della matrice con numeri da 1 a 90
        int numero = 1;
        for (int i = 0; i < tabellaTombola.length; i++) {
            for (int j = 0; j < tabellaTombola[i].length; j++) {
                tabellaTombola[i][j] = numero++;
            }
        }

        // Stampa tabella
        for (int i = 0; i < tabellaTombola.length; i++) {
            for (int j = 0; j < tabellaTombola[i].length; j++) {
                System.out.printf("%2d ", tabellaTombola[i][j]); // Formattazione
            }
            System.out.println();
        }
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci le cordinate per ricerca numeri adiacenti: ");
        int rigaX = scanner.nextInt();
        int rigaY = scanner.nextInt();

        System.out.println("Hai inserito: (" + rigaX + " , " + rigaY + ")");
        System.out.println();

        //Prova metodi esercizio
        stampaElementiAdiacenti(tabellaTombola, rigaX, rigaY);  // metodo con coordinate
        System.out.println();
        System.out.println("Inserisci numero cercato:");
        int numeroCercato = scanner.nextInt();
        scanner.close();
        stampaElementiAdiacentiPerNumero(tabellaTombola, numeroCercato);    // metodo con numero
    }
    // Metodo per stampare gli elementi adiacenti a coordinate (riga, colonna)
    public static void stampaElementiAdiacenti(int[][] tabella, int riga, int colonna) {
        System.out.println();
        // Verifica e stampa l'elemento a sinistra
        if (colonna > 0) {
            System.out.println("Sinistra: " + tabella[riga][colonna - 1]);
        } else {
            System.out.println("Sinistra: Nessun elemento (bordo sinistro)");
        }

        // Verifica e stampa l'elemento a destra
        if (colonna < tabella[0].length - 1) {
            System.out.println("Destra: " + tabella[riga][colonna + 1]);
        } else {
            System.out.println("Destra: Nessun elemento (bordo destro)");
        }

        // Verifica e stampa l'elemento in alto
        if (riga > 0) {
            System.out.println("Alto: " + tabella[riga - 1][colonna]);
        } else {
            System.out.println("Alto: Nessun elemento (bordo superiore)");
        }

        // Verifica e stampa l'elemento in basso
        if (riga < tabella.length - 1) {
            System.out.println("Basso: " + tabella[riga + 1][colonna]);
        } else {
            System.out.println("Basso: Nessun elemento (bordo inferiore)");
        }
    }

    // Metodo per stampare gli elementi adiacenti dato un numero
    public static void stampaElementiAdiacentiPerNumero(int[][] tabella, int numero) {
        // Trova le coordinate del numero
        int riga = -1, colonna = -1;
        boolean trovato = false;

        for (int i = 0; i < tabella.length && !trovato; i++) {
            for (int j = 0; j < tabella[i].length; j++) {
                if (tabella[i][j] == numero) {
                    riga = i;
                    colonna = j;
                    trovato = true;
                }
            }
        }

        // Stampa gli elementi adiacenti se il numero è stato trovato
        if (trovato) {
            stampaElementiAdiacenti(tabella, riga, colonna);
        } else {
            System.out.println("Numero non trovato.");
        }
    }


}