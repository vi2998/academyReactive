package academy.esercizi.esercizio_30;

public class FillInQuestion extends Question {


    /* Con spazi da compilare  (FillInQuestion)
    Passare una stringa di testo con una serie di «spazi» da riempire.
    Ad es. «La capitale dell’Italia è ****,
    si trova nella regione **** ed ha **** abitanti».
    La risposta sarà la stringa completa «La capitale dell’Italia è Roma, si trova nella regione Lazio ed ha 2800000 abitanti»,
    il metodo checkAnswer prende in input le parole separate da «,»
    (ad es. «Roma, Lazio, 2800000» che sostituite agli asterischi
    della domanda dovranno essere uguali alla stringa risposta per essere considerata corretta */


    public void fillInText(String parolePerSostituzione) {

    }

    @Override
    public boolean checkAnswer(String risposta) {

        String[] risposteSeparate = risposta.split(",");
        String text = getDomanda();

        // Sostituisco i gruppi **** con le risposte
        for (int i = 0; i < risposteSeparate.length; i++) {
            text = text.replaceFirst("\\*{4}", risposteSeparate[i]);
        }
        String testo = text;
        return getRisposta().contentEquals(testo);
    }

    @Override
    public void display() {
        String domanda = getDomanda();
        System.out.println(domanda);
    }
}

