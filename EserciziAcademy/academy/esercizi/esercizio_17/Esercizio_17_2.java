package academy.esercizi.esercizio_17;

import java.util.Scanner;

public class Esercizio_17_2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Scrivere se il Microonde è ACCESO o SPENTO");
        String statoMicroonde = scanner.next();

        boolean onOff = false;
        if (statoMicroonde.equalsIgnoreCase("acceso")) {
            onOff = true;
            System.out.println("Inserisci timer in secondi:");
            int timerSecondi = scanner.nextInt();
            System.out.println("Inserisci livello potenza 1 o 2");
            int potenza = scanner.nextInt();
            if (potenza > 0 && potenza < 3) {
                PannelloControlloMicroonde pannelloControlloMicroonde =
                        new PannelloControlloMicroonde(onOff, timerSecondi, potenza);
                boolean avvio = pannelloControlloMicroonde.isAcceso();
                pannelloControlloMicroonde.pulsanteStart(avvio);
                System.out.println();
                System.out.println("Piatto pronto. Reset livelli e spegnimento");
                System.out.println();
                pannelloControlloMicroonde.pulsanteReset();
                System.out.println(pannelloControlloMicroonde);
            } else {
                System.out.println("Livello potenza non valido");
            }
        } else {
            System.out.println("Il microonde è spento");
        }
    }
}

