package academy.esercizi.esercizio_27;

import java.util.Random;

public class Esercizio_27_1 {
    /* Quadrati magici.

    Una matrice n × n riempita con i numeri 1, 2, 3, …, n² è un quadrato magico se la somma degli elementi di ogni riga, di ogni colonna e delle due diagonali ha lo stesso valore. */

    public static void main(String[] args) {
/*        Dovete verificare due caratteristiche:
        - I dati inseriti dall’utente sono presenti tutti i numeri 1, 2, …, 16?
        - Quando i numeri vengono disposti in un quadrato, la somma degli elementi di ogni riga, di ogni colonna e delle due diagonali ha lo stesso valore?
*/
        Esercizio_27_1 esercizio_27_1 = new Esercizio_27_1();
        esercizio_27_1.test();
    }

    public void test() {
        /*Scrivete un programma che legga 16 valori dalla tastiera e verifichi se, disposti in una matrice 4 × 4, formano un quadrato magico.  */
        System.out.println("inserisci la dimensione matrice");
        int dimensioneMatrice = 5;
        int[][] matrice = new int[dimensioneMatrice][dimensioneMatrice];
        matrice[0] = new int[]{17, 24, 1, 8, 15};
        matrice[1] = new int[]{23, 5, 7, 14, 16};
        matrice[2] = new int[]{4, 6, 13, 20, 22};
        matrice[3] = new int[]{10, 12, 19, 21, 3};
        matrice[4] = new int[]{11, 18, 25, 2, 9};



        //matrice = riempiMatrice(matrice);
        stampa(matrice);
        boolean quadratoMagico = checkQuadratoMagico(matrice);
        String risultato = quadratoMagico ? "Il quadrato inserito è un Quadrato magico" : "Il quadrato inserito non è un quadrato magico";
        System.out.println(risultato);
    }

    private boolean checkQuadratoMagico(int[][] matrice) {
        int numeroMagico = (matrice.length * ((matrice.length * matrice.length) + 1) / 2);
        if (!firstcheck(matrice, numeroMagico)) {
            return false;
        } else if (!secondCheck(matrice, numeroMagico)) {
            return false;
        } else if (!terzoCheck(matrice, numeroMagico)) {
            return false;
        } else {
            return ultimoCheck(matrice, numeroMagico);
        }
    }

    private boolean ultimoCheck(int[][] matrice, int numeroMagico) {
        int sommaDiagonaliAlContrario = 0;
        for (int i = 0; i < matrice.length; i++) {
            sommaDiagonaliAlContrario += matrice[matrice.length - 1 - i][i];
        }
        return sommaDiagonaliAlContrario == numeroMagico;
    }

    private boolean terzoCheck(int[][] matrice, int numeroMagico) {
        int sommaDiagonali = 0;
        for (int i = 0; i < matrice.length; i++) {
            sommaDiagonali += matrice[i][i];
        }
        return sommaDiagonali == numeroMagico;
    }

    private boolean secondCheck(int[][] matrice, int numeroMagico) {
        int sommaColonne = 0;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                sommaColonne += matrice[j][i];
            }
            if (sommaColonne != numeroMagico) {
                return false;
            }
            sommaColonne = 0;
        }
        return true;
    }

    private boolean firstcheck(int[][] matrice, int numeroMagico) {
        int sommaRighe = 0;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                sommaRighe += matrice[i][j];
            }
            if (sommaRighe != numeroMagico) {
                return false;
            }
            sommaRighe = 0;
        }
        return true;
    }


    private void stampa(int[][] matrice) {
        for (int[] numero : matrice) {
            for (int i : numero) {
                System.out.print(i + " |");
            }
            System.out.println();
        }
    }

    private int[][] riempiMatrice(int[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = numeroRandom(matrice.length * matrice.length, matrice);
            }
        }
        return matrice;
    }

    private int numeroRandom(int numeroMaxMatrice, int[][] matrice) {
        Random random = new Random();
        int numeroRandom;
        do {
            numeroRandom = random.nextInt(numeroMaxMatrice) + 1;
        } while (!presenzaNumeroRandom(numeroRandom, matrice));
        return numeroRandom;

    }

    private boolean presenzaNumeroRandom(int numeroRandom, int[][] matrice) {
        for (int[] valore : matrice) {
            for (int i : valore) {
                if (i == numeroRandom) {
                    return false;
                }
            }
        }
        return true;
    }


}
