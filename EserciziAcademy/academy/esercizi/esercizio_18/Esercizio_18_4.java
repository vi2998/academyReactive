package academy.esercizi.esercizio_18;

import java.math.BigDecimal;
import java.util.Scanner;

public class Esercizio_18_4 {
    public static void main(String[] args) {
        System.out.println("Inserire saldo conto corrente:");
        Scanner scanner = new Scanner(System.in);
        ContoCorrente saldoCCorrente = new ContoCorrente(scanner.nextInt());
        System.out.println("Hai un saldo sul conto corrente di: " + saldoCCorrente.getSaldo());
        System.out.println("----------------------------------------");
        System.out.println("Inserire saldo conto risparmio:");
        ContoRisparmio saldoCRisparmio = new ContoRisparmio(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Hai un saldo sul conto risparmio di: " + saldoCRisparmio.getSaldo());
        System.out.println("----------------------------------------");

        System.out.println("Inserire il conto sul quale effettuare operazione (conto corrente o conto risparmio): ");
        String contoOperazione = scanner.nextLine();

        if (contoOperazione.equalsIgnoreCase("conto corrente")) {
            System.out.println("Inserisci operazione da effettuare tra: Prelievo - Versamento - Giroconto");
            String operazione = scanner.nextLine().toLowerCase();
            System.out.println("Inserisci un valore positivo per l'operazione:");
            BigDecimal valore = new BigDecimal(scanner.next());

            switch (operazione) {
                case "versamento":
                    System.out.println("Versamento in corso...");
                    saldoCCorrente.versamento(valore);
                    break;
                case "prelievo":
                    System.out.println("Prelievo in corso...");
                    saldoCCorrente.prelievo(valore);
                    break;
                case "giroconto":
                    System.out.println("Bonifico in corso...");
                    saldoCCorrente.bonifico(valore, saldoCRisparmio);
                    break;
                default:
                    System.out.println("Operazione non valida.");
                    break;
            }

            System.out.println("Hai un saldo sul conto corrente di: " + saldoCCorrente.getSaldo());
            System.out.println("Hai un saldo sul conto risparmio di: " + saldoCRisparmio.getSaldo());

        } else if (contoOperazione.equalsIgnoreCase("conto risparmio")) {
            System.out.println("Inserisci operazione da effettuare tra: Prelievo - Versamento - Giroconto");
            String operazione = scanner.nextLine().toLowerCase();
            System.out.println("Inserisci un valore positivo per l'operazione:");
            BigDecimal valore = new BigDecimal(scanner.next());

            switch (operazione) {
                case "versamento":
                    System.out.println("Versamento in corso...");
                    saldoCRisparmio.versamento(valore);
                    break;
                case "prelievo":
                    System.out.println("Prelievo in corso...");
                    saldoCRisparmio.prelievo(valore);
                    break;
                case "giroconto":
                    System.out.println("Bonifico in corso...");
                    saldoCRisparmio.bonifico(valore, saldoCCorrente);
                    break;
                default:
                    System.out.println("Operazione non valida.");
                    break;
            }
            System.out.println("Hai un saldo sul conto corrente di: " + saldoCCorrente.getSaldo());
            System.out.println("Hai un saldo sul conto risparmio di: " + saldoCRisparmio.getSaldo());
        } else {
            System.out.println("Nome conto non valido");
        }

        scanner.close();
    }
}
