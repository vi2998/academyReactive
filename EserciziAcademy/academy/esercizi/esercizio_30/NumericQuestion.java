package academy.esercizi.esercizio_30;

public class NumericQuestion extends Question {

    @Override
    public boolean checkAnswer(String risposta) {
        if (risposta == null || risposta.isEmpty()) {
            System.out.println("Input non valido");
            return false;
        }
        for (int i = 0; i < risposta.length(); i++) {
            if (risposta.charAt(i) < '0' || risposta.charAt(i) > '9') {
                System.out.println("non è stato inserito un numero");
                return false;
            }
        }
        return risposta.equals(getRisposta());
    }

    @Override
    public void display() {
        System.out.println(getDomanda());
    }
}