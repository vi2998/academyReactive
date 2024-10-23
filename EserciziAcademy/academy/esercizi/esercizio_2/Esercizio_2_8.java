package academy.esercizi.esercizio_2;

import java.util.Random;

public class Esercizio_2_8 {
    // Costanti
    public static final int MESCOLA_DADO = 1000;
    public static final int NUM_FACCE = 6;

    public static void main(String[] args) {
        int[] facceDado = new int[NUM_FACCE];

        Random random = new Random();

        for (int i = 0; i < MESCOLA_DADO; i++) {
            int lancio = random.nextInt(NUM_FACCE) + 1;
            facceDado[lancio - 1]++;
        }

        System.out.println("Faccia - Frequenza");
        for (int i = 0; i < facceDado.length; i++) {
            System.out.println((i + 1) + "\t" + facceDado[i]);
        }
    }
}
