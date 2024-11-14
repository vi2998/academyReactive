package academy.esercizi.esercizio_33;

public class BaseFormatter implements NumberFormatter{
    private int base;

    public BaseFormatter(int base){
        if(controlloBase(base)){
            this.base = base;
        }
    }

    private boolean controlloBase(int base) {
        if ( base < 2 || base >36 ){
            System.out.println("Il numero deve essere compreso tra 2 e 36");
            return false;
        }
        return true;
    }

    @Override
    public String format(int n) {
        return Integer.toString(base, n);
    }
}
