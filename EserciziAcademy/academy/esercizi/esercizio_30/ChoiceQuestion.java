package academy.esercizi.esercizio_30;

public class ChoiceQuestion extends Question {

    String[] possibiliRisposte = new String[3];
    int posizioneCorretta;

    //Esercizio 31_2
//    Modificare il progetto per gestire i quiz ed implementare le classi ChoiceQuestion e MultipleChoiceQuestion
//    introducendo le seguenti differenze rispetto alla superclasse Question:

//    - Memorizza le diverse opzioni possibili per la risposta nella variabile choices di tipo String[3]
//    - Memorizza la posizione corretta nella variabile positionCorrect (di tipo int o int[] a seconda della classe)
//    - Ha un metodo per aggiungere le opzioni possibili per la risposta setChoice che prende in input il testo della risposta,
//    la posizione in cui memorizzare il testo ed un boolean per memorizzare se la risposta è corretta nella
//    varabile positionCorrect (che potrà essere solo uno oppure più di uno a seconda della classe)
//    - Il metodo display si differenzia dalla superclasse perché mostra le opzioni possibili e non solo il testo della domanda.

    public void setChoice(String risposta, int indiceRisposta, boolean corretto){
        possibiliRisposte[indiceRisposta] = risposta;
        if (corretto){
            posizioneCorretta = indiceRisposta;
            setRisposta(risposta);
        }
    }

    @Override
    public boolean checkAnswer(String risposta) {
        return risposta.equalsIgnoreCase(getRisposta());
    }

    public void display() {

        System.out.println(getDomanda());

        // stampo risposte
        for (int i = 0; i < possibiliRisposte.length; i++) {
            System.out.println(i + " - "+ possibiliRisposte[i]);
        }
    }
}
