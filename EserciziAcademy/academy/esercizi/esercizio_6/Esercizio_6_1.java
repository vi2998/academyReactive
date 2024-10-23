package academy.esercizi.esercizio_6;

public class Esercizio_6_1 {
    public static void main(String[] args) {

        int[][] matrice = new int[11][4];

        System.out.println("---------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s%n", "x^1", "x^2", "x^3", "x^4");
        System.out.println("---------------------------------------");

        for (int i = 1; i < matrice.length; i++) {
            // Calcola e stampa le potenze di i
            System.out.printf("%-10d %-10d %-10d %-10d%n", (int)Math.pow(i, 1), (int)Math.pow(i, 2), (int)Math.pow(i, 3), (int)Math.pow(i, 4));
        }
    }
}
