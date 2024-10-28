package academy.esercizi.esercizio_18;

import java.math.BigDecimal;
import java.util.Scanner;

public class Esercizio_18_4 {
    public static void main(String[] args) {
        System.out.println("Inserire saldo conto corrente:");   // i valori devono essere positivi
        Scanner scanner = new Scanner(System.in);
        ContoCorrente saldoCCorrente = new ContoCorrente(scanner.nextInt());
        System.out.println("Hai un saldo sul conto corrente di: " + saldoCCorrente.getSaldo());
        System.out.println("----------------------------------------");
        System.out.println("Inserire saldo conto risparmio");
        ContoRisparmio saldoCRisparmio = new ContoRisparmio(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Hai un saldo sul conto risparmio di: " + saldoCRisparmio.getSaldo());
        System.out.println("----------------------------------------");

        // Il valore dell'operazione richiesta deve essere positivo
        System.out.println("Inserire il conto sul quale effettuare operazione (conto corrente o conto risparmio: ");
        String contoOperazione = scanner.nextLine();
        if (contoOperazione.equalsIgnoreCase("conto corrente")) {
            System.out.println("Inserisci operazione da effettuare tra: Prelievo - Versamento - Bonifico");
            String operazione = scanner.nextLine().toLowerCase();
            System.out.println("Inserisci un valore postivo per l'operazione:");
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
                case "bonifico":
                    System.out.println("Bonifico in corso...");
                    saldoCCorrente.bonifico(valore);
                    break;
                default:
                    System.out.println("Operazione non valida.");
                    break;
            }

            // Dopo aver effettuato l'operazione
            System.out.println("Hai un saldo sul conto corrente di: " + saldoCCorrente.getSaldo());

        } else if (contoOperazione.equalsIgnoreCase("conto risparmio")) {

            System.out.println("Inserisci operazione da effettuare tra: Prelievo - Versamento - Bonifico");
            String operazione = scanner.nextLine().toLowerCase();
            System.out.println("Inserisci un valore postivo per l'operazione:");
            BigDecimal valore = new BigDecimal(scanner.next());

            switch (operazione) {

                case "versamento":
                    System.out.println("Inserisci l'importo da versare sul conto corrente:");
                    saldoCRisparmio.versamento(valore);
                    break;
                case "prelievo":
                    System.out.println("Inserisci l'importo da prelevare dal conto corrente:");
                    saldoCRisparmio.prelievo(valore);
                    break;
                case "bonifico":
                    System.out.println("Inserisci l'importo da trasferire dal conto corrente al conto risparmio:");
                    saldoCRisparmio.bonifico(valore);
                    break;
                default:
                    System.out.println("Operazione non valida.");
                    break;
            }
            // Dopo aver effettuato l'operazione
            System.out.println("Hai un saldo sul conto corrente di: " + saldoCRisparmio.getSaldo());
        } else {
            System.out.println("Nome conto non valido");
        }


    }
}
