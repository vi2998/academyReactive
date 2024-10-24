package academy.esercizi.esercizio_1;

public class Esercizio_1_2 {
    public static void main(String[] args) {
        /* Scrivete un programma che calcoli e visualizzi il saldo di un conto bancario dopo il primo, secondo e terzo anno.
        Il conto ha un saldo iniziale di  1000 euro e vi vengono accreditati annualmente interessi pari al 5% del saldo.
        */

        double saldo = 1000;
        final double INTERESSE = 0.05;
        int anni = 3;

        System.out.print("Saldo attuale: " + saldo + "\n");
        for (int i = 1; i <= anni; i++){
            saldo += saldo * INTERESSE;
            System.out.printf("Saldo dopo il %d sarà: %.2f \n", i, saldo);
        }

    }
}