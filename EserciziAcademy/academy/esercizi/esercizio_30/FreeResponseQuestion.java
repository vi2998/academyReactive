package academy.esercizi.esercizio_30;

public class FreeResponseQuestion extends Question {


    @Override
    public boolean checkAnswer(String risposta) {
        return risposta.equalsIgnoreCase(getRisposta());
    }

    @Override
    public void display() {
        String domanda = getDomanda();
        System.out.println(domanda);
    }
}