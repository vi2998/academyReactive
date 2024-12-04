package academy.esercizi.esercizio_38;

import java.util.Optional;
import java.util.Scanner;

public class GiocoDelSognoOptional {

    public void gioca() {
        boolean statoGioco = true;
        int numeroTentativi = 0;
        int punteggio = 0;
        ElementoCasualeOptional elementoCasualeOptional = new ElementoCasualeOptional();
        System.out.println("elementoCasualeOptional.getValore() = " + elementoCasualeOptional.getValore());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Il gioco inizia");

        while (statoGioco) {
            System.out.println("Indovina se il valore è null oppure è valorizzato con un intero");

            Integer valoreUtente = null;
            String input = scanner.nextLine();


            if (input.equalsIgnoreCase("null")) {
                valoreUtente = null;
            } else {
                try {
                    valoreUtente = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Valore inserito non valido, devi inserire un numero o null");
                }
            }

            // valore da indovinare null
            if (valoreUtente != null || input.equalsIgnoreCase("null")) {
                numeroTentativi++;

                // Verifica se il valore è null
                if (!elementoCasualeOptional.getValore().isPresent()) {
                    if (valoreUtente == null) {
                        if (numeroTentativi <= 3) {
                            punteggio += 2;
                        } else {
                            punteggio += 0;
                        }
                    }
                }

                // Caso se il giocatore indovina (valore not null)
                if (elementoCasualeOptional.getValore().isPresent() && elementoCasualeOptional.getValore().get().equals(valoreUtente)) {
                    if (numeroTentativi == 1) {
                        punteggio += 1000;
                    } else if (numeroTentativi == 2 || numeroTentativi == 3) {
                        punteggio += 100;
                    } else if (numeroTentativi >= 4 && numeroTentativi <= 6) {
                        punteggio += 10;
                    } else if (numeroTentativi > 6) {
                        punteggio += 5;
                    }
                    System.out.println("Hai indovinato! Il tuo punteggio è: " + punteggio);
                    statoGioco = false;
                }

                // Caso se il valore da indovinare è 1
                else if (elementoCasualeOptional.getValore().isPresent() && elementoCasualeOptional.getValore().get() == 1) {
                    if (numeroTentativi > 1) {
                        punteggio = -1;  // punteggio è -1 se il valore è 1 ma non indovinato
                    }
                    System.out.println("Il valore da indovinare è 1, gioco terminato.");
                    statoGioco = false;
                }

                // Caso se il giocatore tenta di indovinare null quando il valore è presente (prima di 3 tentativi)
                else if (numeroTentativi <= 3 && !elementoCasualeOptional.getValore().isPresent() && valoreUtente == null) {
                    System.out.println("Hai tentato un valore null, ma il gioco continua.");
                }

                // Caso se il giocatore tenta null ma il valore è presente dopo 3 tentativi
                else if (numeroTentativi > 3 && elementoCasualeOptional.getValore().isPresent() && valoreUtente == null) {
                    System.out.println("Hai tentato null ma il valore è presente, hai perso.");
                    punteggio = -10;  // se il valore è presente e il giocatore tenta null dopo il terzo tentativo
                    statoGioco = false;
                }

                // Caso 5: Il giocatore sbaglia e il valore è presente
                else if (elementoCasualeOptional.getValore().isPresent() && !elementoCasualeOptional.getValore().get().equals(valoreUtente)) {
                    // Dimezzo il valore
                    int nuovoValore = elementoCasualeOptional.getValore().get() / 2;
                    elementoCasualeOptional.setValore(Optional.of(nuovoValore));
                    System.out.println("Hai sbagliato! Il nuovo valore è: " + nuovoValore);
                }

                if (punteggio < 0) {    // gioco termina con punteggio negativo
                    System.out.println("Hai perso! Gioco terminato. Il valore era: " + elementoCasualeOptional.getValore());
                    statoGioco = false;
                }
            }
        }
    }
}
