package academy.esercizi.esercizio_36bis;

import java.util.Comparator;

public class ClasseEsterna implements Comparator<Agenda> {

    private static int priorita(Agenda.Impegni impegni) {
        switch (impegni) {
            case DIVANO:
                return 1;
            case CALCETTO:
                return 2;
            case TEATRO:
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public int compare(Agenda o1, Agenda o2) {
        int priorita1 = priorita(o1.impegno);
        int priorita2 = priorita(o2.impegno);

        return priorita1 - priorita2;
        }
    }

    /*  definisce una priorità delle attività: divano, teatro e calcetto
        (non in ordine come l’enum di Giorni ma ipotizzando che nell’enum impegni i valori siano indicati dal più interessante al meno)
        ed un algoritmo per definire se:
		stampare se ag1 è più interesssante di ag2
		stampare se ag1 è più interesssante di ag3
		stampare se ag1 è più interesssante di ag4*/