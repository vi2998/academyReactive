package academy.esercizi.esercizio_38;

import java.util.Optional;
import java.util.Random;

public class ElementoCasualeOptional {

    private Optional<Integer> valore;

    public ElementoCasualeOptional(Optional<Integer> valore) {
        this.valore = valore;
    }

    public ElementoCasualeOptional() {
       this(setValoreIniziale());
    }

    private static Optional<Integer> setValoreIniziale() {
        Random random = new Random();
        return random.nextInt(2) == 0 ?
                Optional.empty() : Optional.of(random.nextInt((2000 - 100) + 1) + 100);
    }

    public Optional<Integer> getValore() {
        return valore;
    }

    public void setValore(Optional<Integer> valore) {
        this.valore = valore;
    }
}