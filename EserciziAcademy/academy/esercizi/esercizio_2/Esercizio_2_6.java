package academy.esercizi.esercizio_2;

public class Esercizio_2_6 {
    public static void main(String[] args) {
        int[] numeriRandom = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        System.out.println("Array originale:");
        for (int j : numeriRandom) {
            System.out.print(j + " ");
        }

        for (int i = 4; i < numeriRandom.length - 1; i++) {
            numeriRandom[i] = numeriRandom[i + 1];
        }

        numeriRandom[numeriRandom.length - 1] = 0;

        System.out.print("\nArray dopo la rimozione dell'elemento in posizione 4: \n");
        for (int valore : numeriRandom) {
            System.out.print(valore + " ");
        }
    }
}
