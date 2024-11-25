package academy.esercizi.esercizio_37;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Esercizio_37_7 {
    public static void main(String[] args) {
        Esercizio_37_7 esercizio_37_7 = new Esercizio_37_7();
        try {
            esercizio_37_7.test();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void test() throws FileNotFoundException {
        String filepath = "C:\\Users\\SVG.Pitrelli-cons\\Documents\\rootGit\\gitAcademy\\EserciziAcademy\\academy\\esercizi\\esercizio_1\\Esercizio_1_1.java";
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);

        // Mappa per gli identificatori presenti
        HashMap<String, List<Integer>> identificatoriPresenti = new HashMap<>();

        int numeroLinea = 0;
        while (scanner.hasNextLine()) {
            numeroLinea++;
            String linea = scanner.nextLine();
            String[] paroleDellaLinea = linea.split("[^A-Za-z0-9_]+");

            for (String parola : paroleDellaLinea) {
                if (!parola.isEmpty()) {
                    identificatoriPresenti.putIfAbsent(parola, new ArrayList<>());
                    identificatoriPresenti.get(parola).add(numeroLinea);
                }
            }
        }

        // Ordino e stampo
        List<String> identificatoriOrdinati = new ArrayList<>(identificatoriPresenti.keySet());
        Collections.sort(identificatoriOrdinati);

        for (String identificatore : identificatoriOrdinati) {
            List<Integer> righe = identificatoriPresenti.get(identificatore);
            System.out.println(identificatore + " - Righe: " + righe);
        }
        scanner.close();
    }
}