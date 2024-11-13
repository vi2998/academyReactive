package academy.esercizi.esercizio_36;

import java.text.ParseException;

public class PlusMilleException extends ParseException {
    private String messaggio;

    public PlusMilleException(String messaggio, int offset) {
        super("L'importo del versamento deve essere inferiore a 1000", offset);
        this.messaggio = messaggio;
    }


}
