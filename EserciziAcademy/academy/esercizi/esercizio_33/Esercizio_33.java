package academy.esercizi.esercizio_33;

public class Esercizio_33 {
    public static void main(String[] args) {
        Esercizio_33 esercizio_33 = new Esercizio_33();
        esercizio_33.test();

    }

    private void test() {
        NumberFormatter numberFormatter = new DefaultFormatter();
        int numeroDaConvertire = 5;
        String numeroConverito = numberFormatter.format(numeroDaConvertire);
        System.out.println("numeroConverito in stringa = " + numeroConverito);

        System.out.println("--------------------------------");

        NumberFormatter decimaleSeparatorFormatter = new DecimalSeparatorFormatter();
        int numeroDaSeparare = 1000000;
        String numeroSeparato = decimaleSeparatorFormatter.format(numeroDaSeparare);
        System.out.println("numeroSeparato e passato a stringa = " + numeroSeparato);

        System.out.println("--------------------------------");

        NumberFormatter accountingFormatter = new AccountingFormatter();
        int numeroNegativo = -1;
        String numeroConParentesi = accountingFormatter.format(numeroNegativo);
        System.out.println("Da numero negativo a numero con parentesi: " + numeroConParentesi);

        System.out.println("--------------------------------");

        NumberFormatter baseFormatter = new BaseFormatter(2);
        int numero = 5;
        String numeroInBase = baseFormatter.format(numero);
        System.out.println("5 in base 2: " + numeroInBase);

        System.out.println("--------------------------------");

        /* Scrivete un metodo che riceva come parametri un array di numeri intero
            e un oggetto NumberFormatter e visualizzi ciascun numero su una riga separata,
            dopo averlo trasformato in stringa usando l’oggetto NumberFormatter ricevuto.
            I numeri visualizzati devono essere incolonnati a destra. */

        int[] numeri = {1000000, -5, 81};
        NumberFormatter formatter = new DecimalSeparatorFormatter();
        stampaNumeri(numeri, formatter);

    }

    private void stampaNumeri(int[] numeri, NumberFormatter formatter) {
        int maxLength = 0;
        for (int numero : numeri) {
            String formatted = formatter.format(numero);
            maxLength = Math.max(maxLength, formatted.length());
        }

        for (int numero : numeri) {
            String formatted = formatter.format(numero);
            System.out.printf("%" + maxLength + "s%n", formatted);
        }
    }
}

