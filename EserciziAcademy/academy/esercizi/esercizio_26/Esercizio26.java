package academy.esercizi.esercizio_26;

public class Esercizio26 {

    public static void main(String[] args) {
        Esercizio26 esercizio26 = new Esercizio26();
        esercizio26.test();
    }

    private void test() {
        SolitarioBulgaro solitarioBulgaro = new SolitarioBulgaro();
        solitarioBulgaro.gioca(5);
        while (!solitarioBulgaro.finito()){
            solitarioBulgaro.muovi();
        }
        System.out.println("Il gioco è stato completato in: " + solitarioBulgaro.getContaMosse() + " mosse");
    }
}
