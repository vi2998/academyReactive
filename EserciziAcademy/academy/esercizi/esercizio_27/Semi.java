package academy.esercizi.esercizio_27;

public enum Semi {
    CUORI("C"),
    QUADRI("Q"),
    FIORI("F"),
    PICCHE("P");

    private String codice;

    Semi(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }
}
