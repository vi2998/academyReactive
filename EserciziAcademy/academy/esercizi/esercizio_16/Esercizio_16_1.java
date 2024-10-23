package academy.esercizi.esercizio_16;

public class Esercizio_16_1 {
    public static void main(String[] args) {

        verificaSalary();
    }
    public static void verificaSalary(){
        Employee dipendente = new Employee("Ajeje Brazorf", 10000);
        System.out.println(dipendente);
        dipendente.raiseSalary(25);
        System.out.println("Stipendio aggiornato: " + dipendente.getSalary());
    }

}

