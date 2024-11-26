package academy.esercizi.esercizio_37;

import java.util.Scanner;

public class SingleChoiceQuestion extends Question {
    String[] choises;

    public SingleChoiceQuestion(Object text, Object answer, String[] choises) {
        super(text, answer);
        this.choises = choises;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Elementi presenti in choises: ");
        for (int i = 0; i < choises.length; i++) {
            System.out.println(choises[i]);
        }
    }

    @Override
    public boolean checkAnswer(Object answer) {
        return super.answer.equals(answer);
    }

    @Override
    public boolean rispondi(Scanner scanner) {
        display();
        System.out.println("Digita una risposta:");

        Integer rispostaUtente = Integer.valueOf(scanner.nextLine());

        if (rispostaUtente >= 0 && rispostaUtente < choises.length) {
            String risposta = choises[rispostaUtente];

            if (checkAnswer(risposta)) {
                System.out.println("La risposta è corretta");
                return true;
            } else {
                System.out.println("Risposta: " + risposta + " -> non corretta. Riprova");
                return false;
            }
        } else {
            System.out.println("Indice non valido. Riprova");
            return false;
        }
    }
}
