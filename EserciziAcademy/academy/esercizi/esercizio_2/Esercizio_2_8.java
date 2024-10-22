package academy.esercizi.esercizio_2;

import java.util.Random;

public class Esercizio_2_8 {
    public static void main(String[] args) {
        int[] facceDado = new int[6];

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            int lancio = random.nextInt(6) + 1;
            facceDado[lancio - 1]++;
        }

        System.out.println("Faccia - Frequenza");
        for (int i = 0; i < facceDado.length; i++) {
            System.out.println((i + 1) + "\t" + facceDado[i]);
        }
    }
}
