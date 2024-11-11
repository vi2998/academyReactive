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
        int carteUsate = 0;

        for (int i = 0; i < mucchietti; i++) { // metto 1 fino alla lunghezza dei mucchietti
            pile[i] = 1;
            carteUsate++;
        }
        // fino a quando le carte usate sono <45 genero numero random
        for (int i = 0; carteUsate < CARTE; i++){
            pile[i] += numeroCasuale(CARTE - carteUsate);
            carteUsate+= pile[i]-1;
        }
        stampa();
    }

    private int numeroCasuale(int cartePossibiliDaRandom) {
        Random random = new Random();
        return random.nextInt(cartePossibiliDaRandom) + 1;
    }

    public void muovi(){
        int nuovaPila = 0;
        for (int i = 0; i < pile.length; i++) {
            if (pile[i] > 0){
                pile[i]--;  // prendo una carta
                nuovaPila++; // inserisco la carta nella nuova pila
            }
        }
        for (int i = 0; i < pile.length; i++) {
            if (nuovaPila != 0 && pile[i] == 0){
                pile[i] = nuovaPila;
                nuovaPila = 0;  // azzero in modo che non mi va a sostiture le pile che gli vengono dopo
            }
        }
        contaMosse++;
        stampa();
    }

    public void stampa(){
        for (int i = 0; i < pile.length; i++) {
            if (pile[i] != 0){
                System.out.print(pile[i] + " |");
            }
        }
        System.out.println();
    }

    public boolean finito(){
        int[] arrayAppoggio = new int[9];
        for (int i = 1; i < 10; i++) {
            for (int numero : pile) {
                if (numero == i) {
                    arrayAppoggio[i - 1] = 1;
                }
            }
        }
        return checkArray(arrayAppoggio);
    }

    private boolean checkArray(int[] arrayAppoggio) {
        for (int i : arrayAppoggio) {
            if(i != 1){
                return false;
            }
        }
        return true;
    }

    public int getContaMosse() {
        return contaMosse;
    }
}
