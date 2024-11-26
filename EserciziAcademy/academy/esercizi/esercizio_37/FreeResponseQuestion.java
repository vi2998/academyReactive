package academy.esercizi.esercizio_37;

import java.util.Scanner;

public class FreeResponseQuestion extends Question {

    public FreeResponseQuestion(String text, String answer) {
        super(text, answer);
    }

    @Override
    public boolean checkAnswer(Object answer) {
        String risposta = (String) super.answer;
        if(risposta.equalsIgnoreCase((String) answer)){
            return true;
        }
        return false;
    }

    @Override
    public boolean rispondi(Scanner scanner) {
        display();
        System.out.println("Digita la risposta");
        String risposta = scanner.nextLine();
        boolean ricevutoDaCheckAnswer = checkAnswer(risposta);
        if (ricevutoDaCheckAnswer){
            System.out.println("La risposta è corretta");
            return checkAnswer(risposta);
        }
        System.out.println("Risposta: " + risposta + " -> non corretta. " + "Riprova");
        return checkAnswer(risposta);
    }
}
