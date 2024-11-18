package academy.esercizi.esercizio_36bis;

public class Esercizio_36bis {
    public static void main(String[] args) {
        Agenda ag1 = new Agenda(Agenda.Giorni.MARTEDI, Agenda.Impegni.TEATRO);
        Agenda ag2 = new Agenda(Agenda.Giorni.LUNEDI, Agenda.Impegni.CALCETTO);
        Agenda ag3 = new Agenda(Agenda.Giorni.DOMENICA, Agenda.Impegni.DIVANO);
        Agenda ag4 = new Agenda(Agenda.Giorni.LUNEDI, Agenda.Impegni.TEATRO);

        System.out.println("----------- COMPARAZIONE DEI GIORNI -----------"); // IN BASE ALLA POSIZIONE DEGLI ENUM

        System.out.printf("%s %s %d\n", ag1.giornoDellaSettimana, ag2.giornoDellaSettimana, ag1.compareTo(ag2));
        System.out.printf("%s %s %d\n", ag1.giornoDellaSettimana, ag3.giornoDellaSettimana, ag1.compareTo(ag3));
        System.out.printf("%s %s %d\n", ag2.giornoDellaSettimana, ag3.giornoDellaSettimana, ag2.compareTo(ag3));
        System.out.printf("%s %s %d\n", ag1.giornoDellaSettimana, ag4.giornoDellaSettimana, ag1.compareTo(ag4));
        System.out.printf("%s %s %d\n", ag2.giornoDellaSettimana, ag4.giornoDellaSettimana, ag2.compareTo(ag4));

        System.out.println("----------- COMPARAZIONE ESTERNA DEGLI IMPEGNI -----------"); // IN BASE AI VALORI DELLO SWITCH CASE

        ClasseEsterna classeEsterna = new ClasseEsterna();
        System.out.printf("%s %s %d\n", ag1.impegno, ag2.impegno, classeEsterna.compare(ag1, ag2));
        System.out.printf("%s %s %d\n", ag1.impegno, ag3.impegno, classeEsterna.compare(ag1, ag3));
        System.out.printf("%s %s %d\n", ag1.impegno, ag4.impegno, classeEsterna.compare(ag1, ag4));
    }
}
