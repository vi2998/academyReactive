package academy.esercizi.esercizio_16;

public class Employee {

    private String nome;
    private double stipendio;

    public Employee(String employeeName, double currentSalary) {
        this.nome = employeeName;
        this.stipendio = currentSalary;
    }

    public String getName() {
        return nome;
    }

    public double getSalary() {
        return stipendio;
    }

    public void raiseSalary(double byPercent){
        stipendio += stipendio * (byPercent / 100);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "nome='" + nome + '\'' +
                ", stipendio=" + stipendio +
                '}';
    }
}
