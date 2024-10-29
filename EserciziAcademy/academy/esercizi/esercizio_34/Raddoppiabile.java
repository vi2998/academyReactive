package academy.esercizi.esercizio_34;

public interface Raddoppiabile {
    /*L’interfaccia Raddoppiabile. Contiene:
    - La firma del metodo void raddoppia();
    - L’implementazione di default del metodo boolean isDimezzabile che solleva un’eccezione per metodo non implementato; se non si sono ancora viste le eccezioni basti sapere che il corpo del metodo abbia solamente il seguente statement: throw new RuntimeException("NON SONO ANCORA STATO IMPLEMENTATO");
    - Una costante Stringa DESCRIZIONE_CLASSE con il valore "Questa interfaccia raddoppia il valore di un oggetto Raddoppiabile"
    - L’implementazione del metodo static String descrivi() che restituisce DESCRIZIONE_CLASSE
*/

    final String DESCRIZIONE_CLASSE = "Questa interfaccia raddoppia il valore di un oggetto Raddoppiabile";
    void raddoppia();
    default boolean isDimezzabile(){
        throw new RuntimeException("NON SONO ANCORA STATO IMPLEMENTATO");
    }
    static String descrivi(){
        return DESCRIZIONE_CLASSE;
    }
}