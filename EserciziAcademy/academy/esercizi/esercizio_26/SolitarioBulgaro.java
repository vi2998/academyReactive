package academy.esercizi.esercizio_26;

import java.util.Random;

public class SolitarioBulgaro {
    private final int CARTE = 45;
    private int[] pile = new int[CARTE];
    int contaMosse = 0;

    public void gioca(int mucchiettiIniziali){
        configurazioneIniziale(mucchiettiIniziali);

    }

    private void configurazioneIniziale(int mucchietti) {
        int carteDisponibili = 0;
        for (int i = 0; i < mucchietti-1; i++) {
            pile[i] = numeroCasuale(CARTE - (carteDisponibili-i));
            carteDisponibili += pile[i];
        }
        for (int i : pile) {
            if (i != 0){
                System.out.println(i);
            }
        }
    }

    private int numeroCasuale(int cartePossibiliDaRandom) {
        Random random = new Random();
        return random.nextInt(cartePossibiliDaRandom) + 1;
    }

}
