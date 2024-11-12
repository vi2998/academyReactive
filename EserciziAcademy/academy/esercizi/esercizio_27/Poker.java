package academy.esercizi.esercizio_27;

import java.util.Random;
import java.util.Scanner;

public class Poker {
    static final int NUMERO_CARTE = 52;
    Carta[] mazzoCarte = new Carta[NUMERO_CARTE];
    Carta[] carteInMano = new Carta[5];

//    public String determinaPunteggio() {
//        if (isScalaAndColore() && numeroPiuAltoNelMazzo() == 14){
//            return "Hai fatto scala reale";
//        }
//        else if (isScalaColore()){
//            return "Hai fatto scala colore";
//        }
//        else if(isPoker()){
//            return "Hai fatto poker";
//        } else if (isFull()) {
//            return "Hai fatto full";
//        }
//        else if (isColore()){
//            return "Hai fatto colore";
//        } else if (isScala()) {
//            return "Hai fatto colore";
//        } else if (isTris()) {
//            return "Hai fatto tris";
//        } else if (isDoppiaCoppia()) {
//            return "Hai fatto doppia coppia";
//        } else if (isCoppia()) {
//            return "Hai fatto coppia";
//        }
//        else {
//            return "Non hai fatto nessun punteggio";
//        }
//    }
//
//    private boolean isScalaAndColore() {
//        return isColore() && isScala();
//    }

//    private boolean isColore() {
//        // Cinque carte dello stesso seme, con valori non consecutivi
//        for (int i = 0; i < carteInMano.length; i++) {
//
//        }
//    }
    private Semi semi(int posizione) {
        for (Semi value : Semi.values()) {
            if (value.ordinal() == posizione) {
                return value;
            }
        }
        throw new RuntimeException("Errore nella posizione");
    }

    public String unioneNumeroSeme(int valoreCarta, int valoreSeme) {
        StringBuilder semeValore = new StringBuilder();
        semeValore.append(valoreCarta);
        semeValore.append(semi(valoreSeme).getCodice());
        return semeValore.toString();
    }

    public void creaMazzo() {
        int numeroEnum = 0;
        int valoreCarta = 1;
        for (int i = 0; i < NUMERO_CARTE; i++) {
            // Crea una nuova carta con il valore e il seme appropriato
            mazzoCarte[i] = new Carta(valoreCarta, Semi.values()[numeroEnum]);
            valoreCarta++;
            if (valoreCarta == 14) {
                valoreCarta = 1;
                numeroEnum++;
            }
        }
        System.out.println("Mazzo Ordinato:");
        stampaCarte(mazzoCarte);
        mischia();
        System.out.println("Mazzo mischiato:");
        stampaCarte(mazzoCarte);
    }


    private void mischia() {
        Random random = new Random();
        int i = 0;
        while (i < 26) {
            int indiceRandomico = random.nextInt(mazzoCarte.length);
            Carta cartaAppoggio = mazzoCarte[indiceRandomico];
            int indiceRandomicoSecondo = random.nextInt(mazzoCarte.length);
            mazzoCarte[indiceRandomico] = mazzoCarte[indiceRandomicoSecondo];
            mazzoCarte[indiceRandomicoSecondo] = cartaAppoggio;
            i++;
        }
    }

    public void distribusciCarte() {
        System.out.println();
        for (int i = 0; i < carteInMano.length; i++) {
            carteInMano[i] = mazzoCarte[i];
        }
        stampaCarteInMano();
    }

    private void stampaCarteInMano() {
        for (Carta carta : carteInMano) {
            System.out.print("Carte in mano: ");
            System.out.println(carta);
        }
    }

    public void cambiaCarte(Scanner scanner) {
        System.out.println("Inserisci quante carte cambiare:");
        int indiceMazzoPartenza = 5; // ho messo 5 perchè le carte fino a 5 le ho in mano
        int carteDaCambiare = scanner.nextInt();
        for (int i = 0; i < carteDaCambiare; i++) {
            System.out.println("Inserisci l'indice della carte che vuoi sostituire");
            int indiceDaCambiare = scanner.nextInt();
            carteInMano[indiceDaCambiare - 1] = mazzoCarte[indiceMazzoPartenza];
            indiceMazzoPartenza++;
        }
        System.out.println("Carte Cambiate:");
        stampaCarteInMano();
    }

    public void stampaCarte(Carta [] carte) {
        for (Carta carta : carte) {
            System.out.print(carta + " | ");
        }
        System.out.println();
    }
}