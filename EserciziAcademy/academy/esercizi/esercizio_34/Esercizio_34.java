package academy.esercizi.esercizio_34;

public class Esercizio_34 {
    public static void main(String[] args) {
        Raddoppiabile intero = new Intero(12);
        intero.raddoppia();
        Operazione o = new Operazione(((OggettoMatematico) intero).getValore(),
                new Frazione(15, 3).getValore(), '+');

        System.out.println(Raddoppiabile.descrivi());
        try {
            System.out.println(intero.isDimezzabile());
        } catch (Exception e) {
            System.out.println("ERRORE");
        }

        System.out.println(o.stampa());

        Triplicabile secondo = new Intero(18);
        secondo.triplica();
        System.out.println(((OggettoMatematico) secondo).getValore());

        Frazione f = new Frazione(2, 20);
        System.out.println(f.inversa().getValore());
        System.out.println(f.isDimezzabile());

        /*
        che restituisca il seguente output:

        Questa interfaccia raddoppia il valore di un oggetto Raddoppiabile
        ERRORE
        24.0+5.0=29.0
        54.0
        10.0
        true
        */
    }
}