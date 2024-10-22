package academy.esercizi.esercizio_1;

public class Esercizio_1_1 {
    public static void main(String[] args) {
        // Scrivete un programma che calcoli e visualizzi la somma dei primi 10 numeri interi positivi.
        int somma = 0;
        for (int i = 0; i <= 10; i++) {
           somma+= i;
        }
        System.out.println("La somma dei primi 10 numeri interi positivi è: "+ somma);
    }
}
