package academy.esercizi.esercizio_37;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultipleChoiceQuestion extends SingleChoiceQuestion {

    private final List<Integer> answer;

    public MultipleChoiceQuestion(Object text, List<Integer> answer, String[] choices) {
        super(text, answer, choices);
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(Object risposte) {
        if (risposte instanceof List) {
            List<Integer> rispostaUtente = (List<Integer>) risposte;

            return rispostaUtente.size() == answer.size() &&
                    rispostaUtente.containsAll(answer) && answer.containsAll(rispostaUtente);
        }
        return false;
    }

    @Override
    public boolean rispondi(Scanner scanner) {
        display();
        System.out.println("---- Digita le risposte come interi, uno per volta. Termina con 0.");

        List<Integer> rispostaUtente = new ArrayList<>();
        Integer risposta = -1;

        while (risposta != 0) {
            String numero = scanner.nextLine();

            try {
                risposta = Integer.valueOf(numero);
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Riprova con un numero.");
            }

            if (risposta >= 0 && risposta < choises.length && risposta != 0) {
                rispostaUtente.add(risposta);
            } else if (risposta != 0) {
                System.out.println("Indice non valido. Riprova.");
            }
        }

        boolean rispostaCorretta = checkAnswer(rispostaUtente);
        if (rispostaCorretta) {
            System.out.println("La risposta è corretta.");
        } else {
            System.out.println("Le risposte selezionate non sono corrette. Riprova.");
        }
        return rispostaCorretta;
    }
}