package academy.esercizi.esercizio_30;

import java.util.Arrays;

public class MultipleChoiceQuestion extends ChoiceQuestion{
    //    - Il metodo checkAnswer della multipleChoice prende una stringa separata da «,»
//    con l’elenco delle risposte che devono essere tutte corrette.


    int[] posizioniVere = new int[2];
    int indice = 0;


    @Override
    public void setChoice(String risposta, int indiceRisposta, boolean corretto) {

            possibiliRisposte[indiceRisposta] = risposta;
            if (corretto){
                posizioniVere[indice] = indiceRisposta;
                indice++;
            }

    }

    @Override
    public boolean checkAnswer(String risposta) {

        String[] rispostaUtente = risposta.split(",");
        if(rispostaUtente.length != posizioniVere.length){
            return false;
        }
        // coverto la stringa rispostaUtente in una stringa di interi
        int[] rispostaUtenteToIntero = new int[rispostaUtente.length];

        for (int i = 0; i < rispostaUtenteToIntero.length; i++) {
            rispostaUtenteToIntero[i] = Integer.parseInt(rispostaUtente[i].trim());
        }
        Arrays.sort(rispostaUtenteToIntero);
        Arrays.sort(posizioniVere);
        return Arrays.equals(rispostaUtenteToIntero, posizioniVere);
    }
}