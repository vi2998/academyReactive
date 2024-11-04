package academy.esercizi.esercizio_25;

public class Esercizio_25_3 {
    /* Un lucchetto per bicicletta a combinazione numerica ha quattro anelli (ring),
    ciascuno avente i numeri da 0 a 9. Scrivete un programma che, conoscendo i numeri su cui sono attualmente
    posizionati gli anelli e la combinazione di sblocco, visualizzi le istruzioni necessarie a sbloccare il
    lucchetto facendo il numero minimo di rotazioni (twist).
    Una “rotazione verso l’alto” (twist up) aumenta di un’unità il valore presente nell’anello su cui agisce,
    mentre una “rotazione verso il basso” (twist down) lo diminuisce.
    Ad esempio,
    se gli anelli fossero impostati al valore 1729 e la combinazione corretta fosse 5714,
    le istruzioni di sblocco dovrebbe essere queste:
    Ring 1: Twist up 4 times
    Ring 2: Twist up 0 times
    Ring 3: Twist down once
    Ring 4: Twist up or down 5 times
    Ricordate che once significa «una volta».
    Nell’ultimo caso l’istruzione afferma «alto o basso», perché, dovendo fare 5 rotazioni,
    il senso di rotazione è indifferente (potete, però, anche decidere che in tal caso la rotazione sia sempre «verso l’alto»,
    oppure sempre «verso il basso»).
     */

    public static void main(String[] args) {
        final int[] VALORE_ATTUALE = {1, 7, 2, 9};
        final int[] COMBINAZIONE_CORRETTA = {5, 7, 1, 4};
        stampaValoreAttuale(VALORE_ATTUALE);
        stampaCombinazioneCorretta(COMBINAZIONE_CORRETTA);
        rotazioneAnelli(VALORE_ATTUALE, COMBINAZIONE_CORRETTA);
    }

    private static void rotazioneAnelli(int[] valoreAttuale, int[] combinazioneCorretta) {
        for (int i = 0; i < combinazioneCorretta.length; i++) {
            int twistUp = ((combinazioneCorretta[i] - valoreAttuale[i] + 10) % 10);
            int twistDown = ((valoreAttuale[i] - combinazioneCorretta[i] + 10) % 10);

            if (twistUp < twistDown) {
                if (twistUp == 1) {
                    System.out.println("Ring " + (i + 1) + " Twist Up once");
                } else {
                    System.out.println("Ring " + (i + 1) + " Twist Up " + twistUp + " times");
                }
            } else if (twistUp == 0 && twistDown == 0) {
                System.out.println("Ring " + (i + 1) + " no twist");
            } else{
                if (twistDown == 1) {
                    System.out.println("Ring " + (i + 1) + " Twist Down once");
                } else {
                    System.out.println("Ring " + (i + 1) + " Twist Down " + twistDown + " times");
                }
            }
        }
    }



    private static void stampaValoreAttuale(int[] valoreAttuale) {
        for (int i = 0; i < valoreAttuale.length; i++) {
            System.out.print(valoreAttuale[i]);
        }
        System.out.println();
    }

    private static void stampaCombinazioneCorretta(int[] combinazioneCorretta) {
        for (int i = 0; i < combinazioneCorretta.length; i++) {
            System.out.print(combinazioneCorretta[i]);
        }
        System.out.println();
    }
}
