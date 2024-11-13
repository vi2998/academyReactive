package academy.esercizi.esercizio_27;

import java.util.Scanner;

public class Esercizio_27_2 {
    public static void main(String[] args) {
        // VIDEO POKER
        Esercizio_27_2 Esercizio_27_2 = new Esercizio_27_2();
        Esercizio_27_2.test();
    }

    private void test() {
        Poker poker = new Poker();
        poker.creaMazzo();
        poker.distribusciCarte();
        poker.determinaPunteggio();
        System.out.println("Vuoi scartere delle carte?");
        Scanner scanner = new Scanner(System.in);
        boolean risposta = scanner.nextLine().equalsIgnoreCase("si");
        if (risposta) {
            poker.cambiaCarte(scanner);
        } else {
            System.out.println("hai scelto di non cambiare le carte");
        }
        poker.determinaPunteggio();
    }
        /* In questo progetto realizzerete un simulatore del popolare gioco d’azzardo solitamente chiamato “video poker”.
        Il mazzo di carte ne contiene 52, 13 per ciascun seme, e viene mescolato all’inizio del gioco:
        dovete individuare una modalità di mescolamento che sia equa, anche se non è necessario che sia efficiente.
        Successivamente vengono mostrate le prime cinque carte del mazzo al giocatore, che ne può rifiutare alcune, anche tutte, o nessuna.
        Le carte rifiutate vengono sostituite con altre, prelevate ordinatamente dal mazzo.
        A questo punto, sulla base delle cinque carte che il giocatore ha in mano, il programma comunica il punteggio ottenuto,
        che deve essere il maggiore tra i seguenti, elencati in ordine crescente:

• No pair (“Niente”). La configurazione peggiore, che contiene cinque carte spaiate che non compongono alcuna
    delle configurazioni elencate nel seguito.
• One pair (“Coppia”). Due carte dello stesso valore, ad esempio due regine.
• Two pairs (“Doppia coppia”). Due coppie, ad esempio due regine e due cinque.
• Three of a kind (“Tris”). Tre carte dello stesso valore, ad esempio tre regine.
• Straight (“Scala”). Cinque carte con valori consecutivi, non del medesimo seme, come 4, 5, 6, 7 e 8.
    L’asso può precedere il 2 oppure seguire il re.
• Flush (“Colore”). Cinque carte dello stesso seme, con valori non consecutivi.
• Full House (“Full”). Un tris e una coppia, ad esempio tre regine e due 5.
• Four of a kind (“Poker”). Quattro carte con lo stesso valore, ad esempio quattro regine.
• Straight Flush (“Scala colore”). Una scala e, contemporaneamente, un colore: cinque carte con valori consecutivi e dello stesso seme.
• Royal Flush (“Scala reale”). La mano migliore possibile: 10, fante, regina, re e asso, tutti del medesimo seme.
         */
}