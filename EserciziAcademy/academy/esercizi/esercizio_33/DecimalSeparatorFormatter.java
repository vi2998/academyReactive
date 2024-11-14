package academy.esercizi.esercizio_33;

public class DecimalSeparatorFormatter implements NumberFormatter {

    // aggiunge virgole come separatori delle migliaia: ad esempio, il numero un milione viene trasformato nella stringa "1,000,000".

    @Override
    public String format(int numeroSenzaVirgole) {
        String numeroDaDividere = String.valueOf(numeroSenzaVirgole);
        StringBuilder numeroDiviso = new StringBuilder();
        int posizione = 0;
        for (int i = numeroDaDividere.length() - 1; i >= 0; i--) {
            numeroDiviso.append(numeroDaDividere.charAt(i));
            posizione++;
            if (posizione % 3 == 0 && i != 0) {
                numeroDiviso.append(",");
            }
        }
        return numeroDiviso.reverse().toString();
    }
}
