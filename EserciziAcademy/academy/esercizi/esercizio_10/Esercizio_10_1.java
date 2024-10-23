package academy.esercizi.esercizio_10;

import java.util.Scanner;

public class Esercizio_10_1 {

        public static void daIntARomano(int num) {
            String[] migliaia = {"", "M", "MM", "MMM"};
            String[] centinaia = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
            String[] decine = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
            String[] unita = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

            StringBuilder numeroRomano = new StringBuilder();
            numeroRomano.append(migliaia[num / 1000]);
            numeroRomano.append(centinaia[(num % 1000) / 100]);
            numeroRomano.append(decine[(num % 100) / 10]);
            numeroRomano.append(unita[num % 10]);

            System.out.println("Il numero intero diventerà: " + numeroRomano);
        }

        public static void main(String[] args) {
            System.out.println("Inserisci un numero intero. Verrà trasformato in numero romano:");
            Scanner scanner = new Scanner(System.in);
            int numberoIntero = scanner.nextInt();
            daIntARomano(numberoIntero);
        }
    }