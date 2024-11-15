package academy.esercizi.esercizio_29_1_4;

import java.io.*;

public class Esercizio_29_1_4 {
    /* Il formato CSV (l’acronimo di comma-separated values, cioè “valori separati da virgole”) è
    molto usato per memorizzare dati in forma tabulare. Ogni riga della tabella è una riga del file,
    con colonne separate da virgole. I singoli valori possono essere racchiusi tra virgolette, cosa che deve certamente accadere
    se contengono virgole o virgolette. Le virgolette presenti all’interno di valori racchiusi tra virgolette vanno raddoppiate.

    Realizzate la classe CSVReader che legga un file CSV e metta a disposizione i metodi seguenti:
    int numberOfRows()  numero di righe
    int numberOfFields(int row)  numero di campi della riga row
    String field(int row, int column)  campo presente nella riga row e colonna comumn.

    La cosa comoda è che con excel si aprono i CSV.
    Provate a vedere se il file prodotto dal vostro applicativo si apre con excel, modificatelo con questo programma ed
    una volta stampato provate a leggerlo dal vostro programma (fate attenzione ai separatori di colonne che potrebbero variare da «,» a «;»)
     */

    public static void main(String[] args) throws Exception {
        Esercizio_29_1_4 esercizio_29_1_4 = new Esercizio_29_1_4();
        esercizio_29_1_4.test();

    }


    private void test() throws IOException {
        String filePath = "C:\\Users\\SVG.Pitrelli-cons\\Documents\\rootGit\\gitAcademy\\EserciziAcademy\\academy\\esercizi\\esercizio_29_1\\Prova.csv";
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // COLLAUDO METODI CON LE STAMPE
        int contaRighe = numeroRighe(filePath);

        int contaFields = contaCampi(filePath, 0);

        String field = campoSelezionato(2,1);

        System.out.println("numero righe file csv: " + contaRighe);
        System.out.println();
        System.out.println("conta campi nella riga selezionata: " + contaFields);
        System.out.println();
        System.out.println("campo selezionato: " + field);
    }


    // numero di campi della riga riga
    private int contaCampi(String filePath, int riga) {
        int numberOfFields = 0;
        int rigaFileCsv = 0;

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if(rigaFileCsv == riga){
                    String[] fields = line.split(",");
                    numberOfFields = fields.length;
                }
                rigaFileCsv++;
            }
        } catch (IOException e) {
            System.out.println("Errore lettura file: " + e.getMessage());
        }
        return numberOfFields;
    }

    private int numeroRighe(String filePath) {
        int contaRighe = 0;
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                contaRighe++;
            }
        } catch (IOException e) {
            System.out.println("Errore lettura file: " + e.getMessage());
        }
        return contaRighe;
    }

    private String campoSelezionato(int riga, int colonna) {
        int rigaFileCsv = 0;
        String fieldPath = "C:\\Users\\SVG.Pitrelli-cons\\Documents\\rootGit\\gitAcademy\\EserciziAcademy\\academy\\esercizi\\esercizio_29_1\\Prova.csv";
        String field = "";

        try (FileReader fileReader = new FileReader(fieldPath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if(rigaFileCsv == riga){
                    String[] fields = line.split(",");
                    if (colonna >= 0 && colonna < fields.length) {
                        field = fields[colonna];
                    }
                }
                rigaFileCsv++;
            }

        } catch (IOException e) {
            System.out.println("Errore lettura file: " + e.getMessage());
        }
        return field;
    }
}