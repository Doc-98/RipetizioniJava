import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class RiferimentiIncrociati {

    // Struttura dati in forma di TreeMap con comparatore integrato.
    // Usando la keyword "static" rendiamo questo attributo unico e condiviso da ogni istanza di questa classe.
    static TreeMap<String, TreeSet<Integer>> indice =
            new TreeMap<>(
                    (String s1, String s2) -> {
                        if (s1.length() < s2.length()) return -1;
                        if (s1.length() > s2.length()) return 1;
                        return s1.compareTo(s2); // Usiamo un metodo delle stringhe di java per comparare in maniera lessigrafica.
                    }
            );

    // Prende un file in input e ritorna true se la formattazione di ogni linea di testo del file è come segue:
    // "parola":"numero"
    // Ritorna falso altrimenti.
    static boolean verificaFile(File f) throws IOException {

        String regex = "\\w+:\\d+";

        BufferedReader reader = new BufferedReader(new FileReader(f));
        String cursoreLinea = null;

        try {
            for ( ; ; ) {
                cursoreLinea = reader.readLine();
                if (cursoreLinea == null) break;
                if (!cursoreLinea.matches(regex)) return false;
            }
        } finally {
            reader.close();
        }
        return true;
    }//verificaFile

    // Riceve un file come input, lo legge e riempie la struttura dati "indice" con tutte le informazioni all'interno del file.
    static void caricaFile(File f) throws IOException {

        // Istanziamo delle variabili dove salvare le informazioni da analizzare
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String cursoreLinea = null;
        StringTokenizer stringTokenizer = null;

        // Scorriamo il file fino alla fine.
        for ( ; ; ) {

            cursoreLinea = reader.readLine();

            // Creiamo la nostra condizione di uscita
            if (cursoreLinea == null) break;

            // Creiamo il tokenizer passandogli il delimitatore che ci interessa.
            // Siccome non vogliamo contare il delimitatore, non attiviamo il flag finale.
            stringTokenizer = new StringTokenizer(cursoreLinea, ":");

            // Salviamo la parola e il numero contenuti nella linea in due variabili.
            String parolaTemp = stringTokenizer.nextToken();
            int numeroTemp = Integer.parseInt(stringTokenizer.nextToken());

            // Se la parola non è già nella struttura dati, la aggiungiamo.
            if (!indice.containsKey(parolaTemp)) {
                indice.put(parolaTemp, new TreeSet<>());
            }
            // Aggiungiamo il numero al set associato alla parola corrispondente. Non dobbiamo preoccuparci di duplicati essendo un set.
            indice.get(parolaTemp).add(numeroTemp);
        }
        reader.close();
    }

    // Stampa la nostra struttura dati in console.
    static void stampaIndice() {
        for (String cursoreParola : indice.keySet()) {
            System.out.println(cursoreParola);
            System.out.print("\t");
            for (int cursoreNumero : indice.get(cursoreParola))
                System.out.print(cursoreNumero + " ");
            System.out.println();
        }
    }

    // Prendi un file in input e ci scrive dentro la nostra struttura dati in forma testuale.
    static void scriviIndice(File fo) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(fo));
        for (String cursoreParola : indice.keySet()) {
            printWriter.println(cursoreParola);
            printWriter.print("\t");
            for (int n : indice.get(cursoreParola))
                printWriter.print(n + " ");
            printWriter.println();
        }
        printWriter.close();
    }

    public static void main(String[] args) throws IOException {

        // Creiamo uno scanner.
        Scanner scanner = new Scanner(System.in);

        // Creiamo le nostre variabili.
        File fileInput = null, fileOutput = null;
        String nomeFInput = null, nomeFOutput = null;

        // Prendiamo il nome del file input, lo salviamo e lo apriamo.
        System.out.print("Nome file di ingresso: ");
        nomeFInput = scanner.nextLine();
        fileInput = new File(nomeFInput);

        // Gestiamo le eccezioni.
        if (!fileInput.exists())
            throw new RuntimeException("File di ingresso inesistente!");

        // Prendiamo il nome del file output, salviamo ed apriamo anche questo.
        System.out.print("Nome file di uscita: ");
        nomeFOutput = scanner.nextLine();
        fileOutput = new File(nomeFOutput);

        // Gestiamo le eccezioni.
        if (!verificaFile(fileInput))
            throw new RuntimeException("File di ingresso non corretto");

        // Carichiamo i dati nella struttura che abbiamo creato.
        caricaFile(fileInput);
        // Stampiamo la struttura a schermo.
        stampaIndice();
        // Salviamo la nostra struttura dati scrivendola nel file output.
        scriviIndice(fileOutput);
    }//main

}//RiferimentiIncrociati