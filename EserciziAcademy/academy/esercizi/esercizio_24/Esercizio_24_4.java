package academy.esercizi.esercizio_24;

import java.util.Scanner;

public class Esercizio_24_4 {
    public static void main(String[] args) {
        Esercizio_24_4 esercizio_24_4 = new Esercizio_24_4();
        esercizio_24_4.test();
    }

    private void test() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci quanti nomi/valori vuoi inserire: ");
        int totValori = scanner.nextInt(); scanner.nextLine(); // per interrompere il nextInt
        String[] nomi = new String[totValori];
        System.out.println("Inserisci i nomi");
        for (int i = 0; i < nomi.length; i++) {
            nomi[i] = scanner.nextLine();
        }
        System.out.println("---------------------------------------------");
        int[] valori = new int[totValori];
        System.out.println("Inserisci la sequenza di valori");
        for (int i = 0; i < valori.length; i++) {
            valori[i] = scanner.nextInt();scanner.nextLine();
        }

        // Trova il valore massimo
        int valoreMax = valori[0];
        for (int i = 1; i < valori.length; i++) {
            if (valori[i] > valoreMax) {
                valoreMax = valori[i];
            }
        }

        System.out.println("Vuoi stampare in orizzontale o verticale?");
        String modalitaStampa = scanner.nextLine();

        // Stampa il diagramma (modifica il secondo parametro per cambiare modalità)
        stampaDiagramma(nomi, valori, valoreMax, modalitaStampa); // true per orizzontale, false per verticale
    }

    private void stampaDiagramma(String[] nomi, int[] valori, int valoreMax, String modalitaStampa) {
        final int MAX_ASTERISCHI_ORIZZONTALI = 40;
        final int MAX_ASTERISCHI_VERTICALI = 20;

        if (modalitaStampa.equalsIgnoreCase("orizzontale")) {
            for (int i = 0; i < nomi.length; i++) {
                int numeroAsterischi = proporzione(MAX_ASTERISCHI_ORIZZONTALI, valori[i], valoreMax);
                System.out.printf("%-15s ", nomi[i]);
                for (int j = 0; j < numeroAsterischi; j++) {
                    System.out.print("*");
                }
                System.out.println();
            }
        } else if(modalitaStampa.equalsIgnoreCase("verticale")) {
            for (int i = MAX_ASTERISCHI_VERTICALI; i > 0; i--) {
                for (int j = 0; j < nomi.length; j++) {
                    int numeroAsterischi = proporzione(MAX_ASTERISCHI_VERTICALI, valori[j], valoreMax);
                    if (i <= numeroAsterischi) {
                        System.out.print("*  ");
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
            }

            // Stampa i nomi sotto gli asterischi
            for (String nome : nomi) {
                System.out.printf("%-3s", nome);
            }
            System.out.println();
        } else {
            System.out.println("Modalità di stampa non valida. Riprova");
        }
    }

    private static int proporzione(int maxAsterischiVerticali, int valori, int valoreMax) {
        int numeroAsterischi = (int) ((double) valori / valoreMax * maxAsterischiVerticali);
        return numeroAsterischi;
    }
}