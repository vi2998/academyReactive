package academy.esercizi.esercizio_5;

public class Esercizio_5_7 {
    /* Vogliamo rimuovere tutti gli spazi e i trattini presenti nella stringa creditCardNumber: ad esempio,
    se la stringa fosse "4123-5678-9012-3450”, dovremmo trasformarla in "4123567890123450".
    Non usare i metodi replace e replaceAll sulla classe String ma ciclare i singoli caratteri
     */
    public static void main(String[] args) {
        String originale = "4123-5678-9012-3450";
        System.out.println("Stringa originale: " + originale);

        StringBuilder modificata = new StringBuilder();
        for (int i = 0; i < originale.length(); i++){
            if (originale.charAt(i) != '-'){
                modificata.append(originale.charAt(i));
            }
        }
        System.out.println("Stringa modificata: " + modificata);


    }

}
