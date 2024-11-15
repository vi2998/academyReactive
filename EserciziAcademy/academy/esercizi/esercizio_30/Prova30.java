package academy.esercizi.esercizio_30;

public class Prova30 {
    public static void main(String[] args) {

        // Collaudo FreeResponseQuestion
        System.out.println("Collaudo FreeResponseQuestion");
        Question q = new FreeResponseQuestion();
        q.setDomanda("Quale è la capitalia dell’Italia?");
        q.display();
        q.setRisposta("Roma");
        System.out.println(q.getRisposta());
        System.out.println(q.checkAnswer("Roma"));

        System.out.println("---------------------------------------\n");

        // Collaudo FillInQuestion
        System.out.println("Collaudo FillInQuestion");
        Question fraseDaRiempire = new FillInQuestion();
        fraseDaRiempire.setDomanda("La capitale dell'Italia è ****, si trova nella regione **** ed ha **** abitanti");
        fraseDaRiempire.display();
        String testoCorretto = "La capitale dell'Italia è Roma, si trova nella regione Lazio ed ha 2800000 abitanti";
        fraseDaRiempire.setRisposta(testoCorretto);
        String risposta = "Roma, Lazio, 2800000";
        boolean check = fraseDaRiempire.checkAnswer(risposta);
        String testoCompleto = fraseDaRiempire.getRisposta();
        System.out.println(testoCompleto);
        System.out.println("check = " + check);

        System.out.println("---------------------------------------\n");

        // Collaudo NumericQuestion
        System.out.println("Collaudo NumericQuestion");

        Question numericQuestion = new NumericQuestion();
        numericQuestion.setDomanda("Quanti anni hai?");
        numericQuestion.display();
        String eta = "5";
        numericQuestion.setRisposta(eta);
        System.out.println("numericQuestion.getRisposta() = " + numericQuestion.getRisposta());
        System.out.println("numericQuestion.checkAnswer() = " + numericQuestion.checkAnswer(eta));

        System.out.println("---------------------------------------\n");

        // Collaudo ChoiceQuestion
        System.out.println("Collaudo ChoiceQuestion");
        ChoiceQuestion rispostaAScelta = new ChoiceQuestion();
        rispostaAScelta.setDomanda("Chi ha ucciso l'uomo ragno?: \n");
        rispostaAScelta.setChoice("Daniele", 0, false);
        rispostaAScelta.setChoice("Mario", 1, false);
        rispostaAScelta.setChoice("Non si sa", 2, true);
        rispostaAScelta.display();

        System.out.println(rispostaAScelta.checkAnswer("Non si sa"));

        System.out.println("---------------------------------------\n");

        // Collaudo MultipleChoiceQuestion
        System.out.println("Collaudo MultipleChoiceQuestion");
        ChoiceQuestion rispostaMultipla = new MultipleChoiceQuestion();
        rispostaMultipla.setDomanda("Quali sono città del Piemonte: \n");
        rispostaMultipla.setChoice("Asti", 0, true);
        rispostaMultipla.setChoice("Torino", 1, true);
        rispostaMultipla.setChoice("Milano", 2, false);
        rispostaMultipla.display();

        System.out.println(rispostaMultipla.checkAnswer("0,1"));
    }
}
