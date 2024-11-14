package academy.esercizi.esercizio_33;

public class AccountingFormatter implements NumberFormatter{
    @Override
    public String format(int n) {
        String numeroConveritoStringa = String.valueOf(n);
        StringBuilder numeroNegativoConParentesi = new StringBuilder();
        for (int i = 0; i < numeroConveritoStringa.length(); i++){
            if (i == 0){
                numeroNegativoConParentesi.append("(");
            }
            if (numeroConveritoStringa.charAt(i) != '-'){
                numeroNegativoConParentesi.append(numeroConveritoStringa.charAt(i));
            }
        }
        numeroNegativoConParentesi.append(")");
        return numeroNegativoConParentesi.toString();
    }
}
