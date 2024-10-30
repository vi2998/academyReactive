package academy.esercizi.esercizio_22;

import java.util.Scanner;

public class Esercizio_22 {
    public static void main(String[] args) {
        Esercizio_22 esercizio22 = new Esercizio_22();
        esercizio22.test();
    }

    private void test() {
        System.out.println("Inserisci somma da pagare (in centesimi):");
        Scanner scanner = new Scanner(System.in);
        int totale = scanner.nextInt();
        System.out.println("Inserisci somma pagata (in centesimi):");
        int sommaPagata = scanner.nextInt();
        resto(totale, sommaPagata);
    }

    private void resto(int totale, int sommaPagata) {
        int resto = sommaPagata - totale;

        if (resto < 0) {
            System.out.println("Valore sbagliato");
        } else {
            System.out.println("Resto da dare: " + resto + " centesimi");

            for (Denaro moneta : Denaro.values()) {
                int quantita = resto / moneta.getDenaro();
                if (quantita > 0) {
                    System.out.println(quantita + " " + moneta.getNome());
                    resto %= moneta.getDenaro();
                }
            }
        }
    }
}
