package academy.esercizi.esercizio_18;

import java.math.BigDecimal;

public class ContoRisparmio {
    private BigDecimal saldo;

    public ContoRisparmio(int saldo) {
        if (saldo < 0) {
            System.out.println("Saldo non valido. Sono accettati solo valori positivi");
        } else {
            this.saldo = BigDecimal.valueOf(saldo);
        }
    }

    public void versamento(BigDecimal soldiVersati) {
        if (soldiVersati.compareTo(BigDecimal.ZERO) > 0) {
            saldo = saldo.add(soldiVersati);    // aggiungi l'importo al saldo
        } else {
            System.out.println("Importo del versamento non valido");
        }
    }

    public void prelievo(BigDecimal soldiPrelevati) {
        if (saldo.compareTo(soldiPrelevati) >= 0) {
            saldo = saldo.subtract(soldiPrelevati); // Sottrai l'importo dal saldo
        } else {
            System.out.println("Operazione non valida. Il saldo non può essere negativo");
        }
    }

    public void bonifico(BigDecimal soldiDaTrasferire, ContoCorrente contoDestinazione) {
        if (saldo.compareTo(soldiDaTrasferire) >= 0) {
            saldo = saldo.subtract(soldiDaTrasferire);
            contoDestinazione.versamento(soldiDaTrasferire);
            System.out.println("Bonifico di " + soldiDaTrasferire + " effettuato al conto corrente.");
        } else {
            System.out.println("Operazione non valida. Il saldo non può essere negativo");
        }
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
