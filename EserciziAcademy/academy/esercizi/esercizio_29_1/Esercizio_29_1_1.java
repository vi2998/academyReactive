package academy.esercizi.esercizio_29_1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class Esercizio_29_1_1 {
    public static void main(String[] args) throws Exception {
        Esercizio_29_1_1 esercizio_29_1_1 = new Esercizio_29_1_1();
        esercizio_29_1_1.test();
    }

    private void test() throws Exception {
        String path = "C:\\Users\\SVG.Pitrelli-cons\\Documents\\rootGit\\gitAcademy\\EserciziAcademy\\academy\\esercizi\\esercizio_29_1\\TestoDaUsare.txt";
        leggiEModifica(path);
    }

    private void leggiEModifica(String pathname) throws Exception {
        Path path = Paths.get(pathname);
        List<String> righeLette = Files.readAllLines(path);

        Iterator<String> iterator = righeLette.iterator();
        while (iterator.hasNext()) {
            String riga = iterator.next();
            if (riga.isEmpty()) {
                iterator.remove();
            }
        }

        System.out.println("Contenuto modificato (senza righe vuote):");
        System.out.println(righeLette);

        Files.write(path, righeLette);
    }
}