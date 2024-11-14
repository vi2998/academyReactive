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
        System.out.println("Da numero negativo a numero con parentesi: " + numeroConParentesi);;

    }
}
