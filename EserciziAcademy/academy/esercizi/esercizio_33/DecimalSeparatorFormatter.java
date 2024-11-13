package academy.esercizi.esercizio_33;

public class DecimalSeparatorFormatter implements NumberFormatter{

    // aggiunge virgole come separatori delle migliaia: ad esempio, il numero un milione viene trasformato nella stringa "1,000,000".

    private int numeroSenzaVirgole;

    @Override
    public String format(int numeroSenzaVirgole) {
        String numeroDaDividere = String.valueOf(numeroSenzaVirgole);

        //TODO FINIRE
        StringBuilder numeroDiviso = new StringBuilder();
        for (int i = numeroDaDividere.length(); i > 0; i--){

        }
    }
}
