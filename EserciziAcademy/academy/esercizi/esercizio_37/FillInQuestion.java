package academy.esercizi.esercizio_37;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FillInQuestion extends Question{

    public FillInQuestion(String text, List<String> answer) {
        super(text, answer);
        if(numeroRisposteDaDAre() != answer.size()){
            throw new RuntimeException("La lista delle risposte non coincide con gli spazi da riempire");
        }
    }

    private int numeroRisposteDaDAre() {
        int contaRisposteVuote = 0;
        String testo = (String) super.text;
        while (testo.contains("****")){
            testo = testo.replaceFirst("\\*{4}", "");
            contaRisposteVuote++;
        }
        return contaRisposteVuote;
    }

    @Override
    public boolean checkAnswer(Object answer) {
        List<String> risposteUtente = (List<String>) answer;
        List<String> answersList = (List<String>) super.answer;
        if (risposteUtente.size() == answersList.size()){
            Collections.sort(risposteUtente);
            Collections.sort(answersList);
            return risposteUtente.equals(answersList);
        }
        return false;
    }

    @Override
    public boolean rispondi(Scanner scanner) {
        display();
        System.out.println("Digita le risposte da inserire (separate da un invio)");
        List<String> risposte = new ArrayList<>();
        while (risposte.size() != ((List<String>)super.answer).size()){
            risposte.add(scanner.nextLine());
        }
        if (checkAnswer(risposte)){
            System.out.println("La risposta è corretta");
            return true;
        }
        String textCompleto = (String)text;
        for (String risposta : risposte) {

            textCompleto = textCompleto.replaceFirst("\\*{4}", risposta);
        }
        System.out.println("Risposta: " + textCompleto + " -> non corretta. " + "Riprova");
        return false;
    }
}
