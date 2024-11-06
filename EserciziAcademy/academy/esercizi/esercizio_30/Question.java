package academy.esercizi.esercizio_30;

public abstract class Question {
    private String domanda;
    private String risposta;


    public Question() {
    }

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public String getRisposta() {
        return risposta;
    }

    public String getDomanda() {
        return domanda;
    }

    public abstract boolean checkAnswer(String risposta);

    public abstract void display();
}
