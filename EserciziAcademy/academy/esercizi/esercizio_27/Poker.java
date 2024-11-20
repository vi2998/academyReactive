package academy.esercizi.esercizio_27;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Poker {
    static final int NUMERO_CARTE = 52;
    static final int CARTE_IN_MANO = 5;
    Carta[] mazzoCarte = new Carta[NUMERO_CARTE];
    Carta[] carteInMano = new Carta[CARTE_IN_MANO];

    public String determinaPunteggio() {
        if (isScalaAndColore() && cartaPiuAltaNellaMano() == 14) {
            return "Hai fatto scala reale";
        } else if (isScalaAndColore()) {
            return "Hai fatto scala colore";
        } else if (isPoker()) {
            return "Hai fatto poker";
        } else if (isFull()) {
            return "Hai fatto full";
        } else if (isColore()) {
            return "Hai fatto colore";
        } else if (isScala()) {
            return "Hai fatto scala";
        } else if (isTris()) {
            return "Hai fatto tris";
        } else if (isDoppiaCoppia()) {
            return "Hai fatto doppia coppia";
        } else if (isCoppia()) {
            return "Hai fatto coppia";
        }
        return "Non hai fatto nessun punteggio";
    }


    private boolean isScalaAndColore() {
        return isColore() && isScala();
    }

    private int cartaPiuAltaNellaMano() {
        int maxValore = 0;
        for (int i = 0; i < carteInMano.length; i++) {
            if (!presenzaCarteDueInMano() && carteInMano[i].getValore() == 1) {
                maxValore = 14; // se non ho 2 in mano non posso fare scala da 1 a 5 ma posso farlo da 14 a 10
            }
            if (carteInMano[i].getValore() > maxValore) {
                maxValore = carteInMano[i].getValore();
            }
        }
        return maxValore;
    }

    private boolean presenzaCarteDueInMano() {
        for (Carta carta : carteInMano) {
            if (carta.getValore() == 2) {
                return true;
            }
        }
        return false;
    }


    // Quattro carte con lo stesso valore
    private boolean isPoker() {
        for (int i = 0; i < carteInMano.length; i++) {
            int carteUguali = 1;  // Parto dalla prima carta
            for (int j = i + 1; j < carteInMano.length; j++) {
                if (carteInMano[i].getValore() == carteInMano[j].getValore()) {
                    carteUguali++;
                }
            }
            if (carteUguali == 4) {
                return true;
            }
        }
        return false;
    }

    // un tris e una coppia
    private boolean isFull() {
        return isTris() && isCoppia();
    }

    // 5 carte dello stesso seme
    private boolean isColore() {
        for (int i = 0; i < carteInMano.length - 2; i++) {
            if (carteInMano[i].getSeme() != carteInMano[i + 1].getSeme()) {
                return false;
            }
        }
        return true;
    }

    // Cinque carte con valori consecutivi
    private boolean isScala() {

        for (int i = cartaPiuAltaNellaMano() - 1; i > cartaPiuAltaNellaMano() - 4; i--) {
            boolean controllo = false;
            for (Carta carta : carteInMano) {
                if (carta.getValore() == i) {
                    controllo = true;
                }
            }
            if (!controllo) {
                return false;
            }
        }
        return true;
    }


    // Tre carte dello stesso valore
    private boolean isTris() {
        for (int i = 0; i < carteInMano.length; i++) {
            int carteUguali = 1;  // Parto dalla prima carta
            for (int j = i + 1; j < carteInMano.length; j++) {
                if (carteInMano[i].getValore() == carteInMano[j].getValore()) {
                    carteUguali++;
                }
            }
            if (carteUguali == 3) {
                return true;
            }
        }
        return false;
    }


    private boolean isCoppia() {
        int[] arrayContaCarteInManoPerValore = new int[15];  // non devo tener conto della posizione 0
        for (Carta carta : carteInMano) {
            arrayContaCarteInManoPerValore[carta.getValore()]++; // Incrementa il contatore per il valore della carta che ho
        }
        int coppie = 0;
        for (int count : arrayContaCarteInManoPerValore) {
            if (count == 2) { // count == 2 per vedere se di quella carta ne ho 2 --> quindi ho una coppia
                coppie++;
            }
        }
        return coppie == 1;
    }

    private boolean isDoppiaCoppia() {
        int[] arrayContaCarteInManoPerValore = new int[15];  // non devo tener conto della posizione 0
        for (Carta carta : carteInMano) {
            arrayContaCarteInManoPerValore[carta.getValore()]++;
        }
        int coppie = 0;
        for (int count : arrayContaCarteInManoPerValore) {
            if (count == 2) {
                coppie++;
            }
        }
        return coppie == 2;
    }

    public Semi semi(int posizione) {
        for (Semi value : Semi.values()) {
            if (value.ordinal() == posizione) {
                return value;
            }
        }
        throw new RuntimeException("Errore nella posizione");
    }

    public String unioneNumeroSeme(int valoreCarta, int valoreSeme) {
        String semeValore = valoreCarta + semi(valoreSeme).getCodice();
        return semeValore;
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
        System.out.print("Carte in mano: ");
        for (Carta carta : carteInMano) {
            System.out.print(carta + " | ");
        }
        System.out.println();
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

    public void stampaCarte(Carta[] carte) {
        for (Carta carta : carte) {
            System.out.print(carta + " | ");
        }
        System.out.println();
    }

    //test per scala colore e scala reale
    public void setCarteInMano() {
        for (int i = 0; i < carteInMano.length-1; i++) {
            carteInMano[i] = new Carta(13 - i, semi(0));
        }
        carteInMano[carteInMano.length-1] = new Carta(1,semi(0));
        stampaCarte(carteInMano);
    }
}