package academy.esercizi.esercizio_30;

public class ChoiceQuestion extends Question{
    @Override
    public boolean checkAnswer(String risposta) {
        return false;
    }

    @Override
    public void display() {
        System.out.println(getDomanda());
    }
}
