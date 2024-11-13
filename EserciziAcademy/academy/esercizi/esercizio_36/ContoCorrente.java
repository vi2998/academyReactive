package academy.esercizi.esercizio_36;

public class ContoCorrente {
    private int saldo;
    public String numeroConto;

    public ContoCorrente(String numeroConto, int saldo) {
        this.numeroConto = numeroConto;
        this.saldo = saldo;
    }

    /*Versa(int importo) che incrementa il saldo del valore di importo.
    - Nel caso il versamento superi i 1000 euro sollevi un’eccezione custom che estende ParseException (questa classe è una forzatura per l’esercizio)

    - Preleva(int importo) che decrementa il saldo del valore di importo.
    In caso di importo>saldo restituire eccezione custom che riporti saldocorrente, numeroconto e importo che si desiderava prelevare
    (memorizzate come variabili d’istanza della custom exception).
*/
    public void versa(int importo) throws PlusMilleException {
        if (importo > 1000){
            throw new PlusMilleException("L'importo deve essere inferiore a 1000", 1000);
        }
        saldo+= importo;
    }

    public void preleva(int importo) throws SaldoNegativoException {
        if (importo > saldo) {
            throw new SaldoNegativoException(saldo, numeroConto, importo);
        }
        saldo -= importo;
    }

    public double getSaldo() {
        return saldo;
    }
}
