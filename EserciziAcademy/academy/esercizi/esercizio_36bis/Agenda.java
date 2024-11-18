package academy.esercizi.esercizio_36bis;

public class Agenda implements Comparable<Agenda>{

    enum Giorni {LUNEDI, MARTEDI, MERCOLEDI, GIOVEDI, VENERDI, SABATO, DOMENICA;}

    enum Impegni {TEATRO, DIVANO, CALCETTO;}

    Giorni giornoDellaSettimana;

    Impegni impegno;

    public Agenda(Giorni giornoDellaSettimana, Impegni impegno) {
        this.giornoDellaSettimana = giornoDellaSettimana;
        this.impegno = impegno;
    }


    @Override
    public int compareTo(Agenda giorno) {
        return giorno.giornoDellaSettimana.compareTo(giornoDellaSettimana);
    }
}
