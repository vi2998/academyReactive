package academy.esercizi.esercizio_9;

import java.util.Scanner;

public class Esercizio_9_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// FIXARE SOTTO
        System.out.println("Inserisci coordinate punto A (x y): ");
        int[] verticeA = {scanner.nextInt(), scanner.nextInt()};

        System.out.println("Inserisci coordinate punto B (x y): ");
        int[] verticeB = {scanner.nextInt(), scanner.nextInt()};

        System.out.println("Inserisci coordinate punto C (x y): ");
        int[] verticeC = {scanner.nextInt(), scanner.nextInt()};

        System.out.println("Inserisci coordinate punto D (x y): ");
        int[] verticeD = {scanner.nextInt(), scanner.nextInt()};

        scanner.close();

        stampaDisegno(verticeA, verticeB, verticeC, verticeD);
    }

    public static void stampaDisegno(int[] A, int[] B, int[] C, int[] D) {
        int[] xCoord = {A[0], B[0], C[0], D[0]};
        int[] yCoord = {A[1], B[1], C[1], D[1]};

        // Valori massimi per dimensioni della matrice
        int maxX = Math.max(Math.max(xCoord[0], xCoord[1]), Math.max(xCoord[2], xCoord[3])) + 1;
        int maxY = Math.max(Math.max(yCoord[0], yCoord[1]), Math.max(yCoord[2], yCoord[3])) + 1;

        String[][] disegno = new String[maxY][maxX];

        // Riempiamo la matrice con "O"
        for (int i = 0; i < disegno.length; i++) {
            for (int j = 0; j < disegno[i].length; j++) {
                disegno[i][j] = "O";
            }
        }

        // Posizioniamo "X" nei vertici e stampo
        disegno[A[1]][A[0]] = "X"; // y, x
        disegno[B[1]][B[0]] = "X";
        disegno[C[1]][C[0]] = "X";
        disegno[D[1]][D[0]] = "X";

        for (String[] riga : disegno) {
            for (String cella : riga) {
                System.out.print(cella + " ");
            }
            System.out.println();
        }

        // Determina il tipo di figura
        String tipoFigura = determinaFigura(A, B, C, D);
        System.out.println("La figura è: " + tipoFigura);
    }

    public static String determinaFigura(int[] A, int[] B, int[] C, int[] D) {
        // Calcola le distanze tra i punti
        double AB = distanza(A, B);
        double BC = distanza(B, C);
        double CD = distanza(C, D);
        double DA = distanza(D, A);
        double AC = distanza(A, C);
        double BD = distanza(B, D);

        boolean latiUguali = (AB == BC) && (BC == CD) && (CD == DA); // Tutti i lati uguali
        boolean diagonaliUguali = (AC == BD); // Diagonali uguali
        boolean rettangolo = (AB == CD) && (BC == DA); // Lati opposti uguali

        // Controllo per triangoli rettangoli
        boolean triangoloRet = (Math.pow(AB, 2) + Math.pow(BC, 2) == Math.pow(AC, 2)) ||
                (Math.pow(AB, 2) + Math.pow(AC, 2) == Math.pow(BC, 2)) ||
                (Math.pow(BC, 2) + Math.pow(AC, 2) == Math.pow(AB, 2));

        if (latiUguali && diagonaliUguali) {
            return "Quadrato";
        } else if (latiUguali) {
            return "Rombo";
        } else if (rettangolo) {
            return "Rettangolo";
        } else if (triangoloRet) {
            return "Trapezio Rettangolo";  //FIXME
        } else {
            return "Non ha nessuna di queste forme";
        }
    }

    public static double distanza(int[] punto1, int[] punto2) {
        // Calcola la distanza tra due punti
        return Math.sqrt(Math.pow(punto1[0] - punto2[0], 2) + Math.pow(punto1[1] - punto2[1], 2));
    }
}
