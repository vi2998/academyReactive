package academy.esercizi.esercizio_36;

public class Esercizio_36_3 {

    public static void main(String[] args) throws PlusMilleException, SaldoNegativoException {
        Esercizio_36_3 esercizio_36_3 = new Esercizio_36_3();
        esercizio_36_3.test();
    }

    private void test() throws PlusMilleException, SaldoNegativoException {
        ContoCorrente conto = new ContoCorrente("IT1234567890", 100);

        int versamento = 2000;
        int prelievo = 900;

        // collaudo metodi con le eccezioni custom

        conto.versa(versamento);
        System.out.println("Saldo conto: " + conto.getSaldo());
        conto.preleva(prelievo);
        System.out.println("Saldo conto: " + conto.getSaldo());
    }
}

