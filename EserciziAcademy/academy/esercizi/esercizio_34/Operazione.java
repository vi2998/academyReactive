package academy.esercizi.esercizio_34;

public class Operazione extends OggettoMatematico{
    /*La classe Operazione che estende OggettoMatematico e
    rappresenta un’operazione aritmetica tra numeri ed una operazione
    a scelta tra addizione, sottrazione, moltiplicazione e divisione.
    Contiene:
tre variabili di istanza private: a tra + - * /
un costruttore pubblico con i tre argomenti op1,op2,op.
i getter delle variabili d’istanza
il metodo ereditato getValore() che restituisce il valore double calcolato mediante l'espressione
il metodo ereditato stampa() sovrascritto per restituiree una una stringa nella forma «op1 op op2 = valore»  usando uno StringBuilder e richiamando getValore()
*/
    private double op1; // (operando 1)
    private double op2; // (operando 2)
    private char op; // (operazione) che rappresentano i due operandi e l'operazione scelta tra +-*/

    public Operazione(double op1, double op2, char op) {

        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
    }

    public double getOp1() {
        return op1;
    }

    public double getOp2() {
        return op2;
    }

    public char getOp() {
        return op;
    }

    @Override
    public double getValore() {
        switch (op) {
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
            case '*':
               return op1 * op2;
            case '/':
               return op1 / op2;
            default:
                return -1;
        }
    }

    //restituire una una stringa nella forma «op1 op op2 = valore»  usando uno StringBuilder e richiamando getValore()

    public String stampa() {
        StringBuilder sb = new StringBuilder();
        sb.append("<<")
                .append(" ").append(op1)
                .append(" ").append(op)
                .append(" ").append(op2)
                .append(" = ").append(getValore())
                .append(" >>");
        return sb.toString();
    }

}