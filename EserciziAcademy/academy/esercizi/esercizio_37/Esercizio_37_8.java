package academy.esercizi.esercizio_37;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Esercizio_37_8 {
    public static void main(String[] args) {
        Esercizio_37_8 esercizio_37_8 = new Esercizio_37_8();
        esercizio_37_8.test();
    }

    private void test() {
//        List<String> risposte = new ArrayList<>();
//        risposte.add("Roma");
//        risposte.add("Lazio");
//        risposte.add("2800000");
//        //risposte.add("test per far apparire l'eccezione");
//        Question q1 = new FillInQuestion("La capitale dell'Italia è ****, si trova nella regione **** ed ha **** abitanti", risposte);
//        q1.rispondi(new Scanner(System.in));
//
//        System.out.println("-----------------------");
//        Question q2 = new FreeResponseQuestion("Quale è la capitalia dell’Italia?", "Roma");
//        q2.rispondi(new Scanner(System.in));
//
//        System.out.println("-----------------------");

//        String[] choises = {"Roma", "Torino", "Milano"};
//        Question q3 = new SingleChoiceQuestion("Capoluogo del Piemonte? (selezionare risposta con 0-2)", "Torino", choises);
//        q3.rispondi(new Scanner(System.in));
//
//        System.out.println("-----------------------");

        String[] choises = {"Roma", "Torino", "Milano"};
        SingleChoiceQuestion q4 = new MultipleChoiceQuestion();
    }
}
