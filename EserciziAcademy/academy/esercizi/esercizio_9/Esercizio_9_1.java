package academy.esercizi.esercizio_9;

import java.util.Scanner;

public class Esercizio_9_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserire nazionalità italiana o americana:");
        String nazionalita = scanner.nextLine();

        System.out.println("Inserire RAL:");
        double ral = scanner.nextDouble();

        if (ral > 0) {
            if (nazionalita.equalsIgnoreCase("americana")) {
                System.out.print("Sei coniugato? (si/no): ");
                String statoCivile = scanner.next();
                scanner.close();
                calcolaTasseAmericano(ral, statoCivile);
            } else if (nazionalita.equalsIgnoreCase("italiana")) {
                scanner.close();
                calcolaTasseItaliano(ral);
            } else {
                System.out.println("Nazionalità non valida per il calcolo della tassazione");
                scanner.close();
            }
        } else {
            System.out.println("La RAL deve essere maggiore di 0!");
        }
    }

    public static void calcolaTasseAmericano(double ral, String statoCivile) {
        double tax;
        //americano non coniugato
        if (statoCivile.equalsIgnoreCase("no")) {
            if (ral >= 0 && ral <= 8000) {
                tax = ral * 0.10; // 10% della RAL
            } else if (ral > 8000 && ral <= 32000) {
                tax = 800 + (ral - 8000) * 0.15; // 800 + 15% sulla parte superiore a 8000
            } else {
                tax = 4400 + (ral - 32000) * 0.25; // 4400 + 25% sulla parte superiore a 32000
            }
        } else {
            // americano coniugato
            if (ral >= 0 && ral <= 16000) {
                tax = ral * 0.10; // 10% di RAL
            } else if (ral > 16000 && ral <= 64000) {
                tax = 1600 + (ral - 16000) * 0.15; // 1600 + 15% sulla parte superiore a 16000
            } else {
                tax = 8800 + (ral - 64000) * 0.25; // 8800 + 25% sulla parte superiore a 64000
            }
        }
        System.out.println("RAL netta: " + (int) (ral - tax));
    }

    public static void calcolaTasseItaliano(double ral) {
        double tasse;

        if (ral >= 0 && ral <= 15000) {
            tasse = ral * 0.23; // primo scaglione
        } else if (ral > 15000 && ral <= 28000) {
            tasse = (15000 * 0.23) + (ral - 15000) * 0.25; // secondo scaglione
        } else if (ral > 28000 && ral <= 50000) {
            tasse = (15000 * 0.23) + (13000 * 0.25) + (ral - 28000) * 0.35; // terzo scaglione
        } else {
            tasse = (15000 * 0.23) + (13000 * 0.25) + (22000 * 0.35) + (ral - 50000) * 0.43; // quarto scaglione
        }

        System.out.println("RAL netta: " + (int) (ral - tasse));
    }


}