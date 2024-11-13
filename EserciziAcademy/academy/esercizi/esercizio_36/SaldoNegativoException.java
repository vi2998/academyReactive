package academy.esercizi.esercizio_36;

public class SaldoNegativoException extends RuntimeException {
    private int saldoCorrente;
    private String numeroConto;
    private int importoDaPrelevare;

    public SaldoNegativoException(int saldoCorrente, String numeroConto, int importoDaPrelevare) {
       super("L'importo desiderato: " + importoDaPrelevare + " è maggiore del saldo:" + saldoCorrente);
        this.saldoCorrente = saldoCorrente;
        this.numeroConto = numeroConto;
        this.importoDaPrelevare = importoDaPrelevare;
    }

    public int getSaldoCorrente() {
        return saldoCorrente;
    }

    public String getNumeroConto() {
        return numeroConto;
    }

    public int getImportoDaPrelevare() {
        return importoDaPrelevare;
    }
}
