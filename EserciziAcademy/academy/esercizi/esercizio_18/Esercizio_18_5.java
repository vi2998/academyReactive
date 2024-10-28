package academy.esercizi.esercizio_18;

import java.math.BigDecimal;

public class Esercizio_18_5 {
    public static void main(String[] args) {

        /* Scrivete un programma che calcoli e visualizzi il saldo di un conto bancario dopo il primo anno.
           Il conto ha un saldo iniziale di 1824 euro e vi vengono accreditati interessi pari al 0,69%
           del saldo.
        */

        final double SALDO_INIZIALE = 1824;
        final BigDecimal INTERESSE = new BigDecimal("0.69").divide(new BigDecimal("100"));

        // Calcolo del saldo
        BigDecimal saldoIniziale = new BigDecimal(SALDO_INIZIALE);
        BigDecimal interesseAccreditato = saldoIniziale.multiply(INTERESSE);
        BigDecimal saldoAggiornato = saldoIniziale.add(interesseAccreditato);

        System.out.println("Il saldo dopo il primo anno è uguale a: " + saldoAggiornato);
    }
}
