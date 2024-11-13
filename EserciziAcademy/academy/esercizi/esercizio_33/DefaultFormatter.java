package academy.esercizi.esercizio_33;

public class DefaultFormatter implements NumberFormatter{

    // trasforma in stringa un numero intero nel modo consueto

    private int numeroDaConvertireInStringa;

    @Override
    public String format(int numeroDaConvertireInStringa) {
        return String.valueOf(numeroDaConvertireInStringa);
    }
}
