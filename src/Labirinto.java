import java.util.*;
import java.io.*;

public class Labirinto {

    // Attributi
    private int[][] mappa;
    private boolean trovataSoluzione = false;

    // Riceve un file e ritorna true se il file è valido se e riesce a caricarlo correttamente. False altrimenti.
    public boolean caricaFile(File f) throws IOException {

        // Verifica esistenza.
        if (!f.exists()) return false;

        // Creiamo reader e stringa cursore.
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String linea = reader.readLine();

        // Se il file è vuoto o se la prima linea non è ben costruita, ritorniamo false.
        if (linea == null || !linea.matches("[\\+\\-]?\\d+")) return false;

        // Salviamo la prima linea come numero in una variabile intera e verifichiamo che sia un numero valido.
        int num = Integer.parseInt(linea);
        if (num < 1 || num > 9999) return false;

        // Allochiamo la memoria per la mappa con le dimensioni appropriate.
        mappa = new int[num + 1][4];

        // Scorriamo il file e ci teniamo un contatore di linea.
        int cont = 0;
        for (linea = reader.readLine(); linea != null; linea = reader.readLine(), cont++) {
            // Se una linea non è ben costruita ritorniamo false.
            if (!linea.matches("\\d+([ \\-\\$]\\d+){4}")) return false;

            // Creiamo un tokenizer e gli passiamo un delimitatore aggiuntivo.
            StringTokenizer tokenizer = new StringTokenizer(linea, " -$");
            // Prendiamo il primo numero.
            num = Integer.parseInt(tokenizer.nextToken());

            // Se il numero è fuori dal range corretto ritorniamo false.
            if (num < 1 || num > mappa.length) return false;
            // Scorriamo i seguenti 4 numeri.
            for (int i = 0; i < 4; i++) {
                // Prendiamo un numero.
                int cursor = Integer.parseInt(tokenizer.nextToken());
                // Se non è valido ritorniamo false.
                if (cursor < 0 || cursor > mappa.length && cursor != 9999 || cursor == num) return false;
                // Salviamo il numero nella posizione corretta.
                mappa[num][i] = cursor;
            }
        }
        // Se abbiamo letto un numero di linee uguale al numero di righe della mappa, ritorniamo true. False altrimenti.
        return cont == mappa.length - 1;
    }

    // Prende un numero che rappresenta il punto di partenza e ritorna true se esiste una soluzione.
    public boolean risolvi(int start) {
        // Impostiamo l'attributo booleano a false.
        this.trovataSoluzione = false;
        // Creiamo una linked list per immagazzinare il percorso.
        LinkedList<Integer> percorso = new LinkedList<Integer>();
        // Gli aggiungiamo il punto di partenza come primo elemento.
        percorso.add(start);
        // Facciamo partire il metodo ricorsivo.
        trovaPercorso(start, percorso);
        // Ritorniamo l'attributo.
        return this.trovataSoluzione;
    }

    // Riceve un intero che rappresenta la stanza che deve guardare e la lista con il percorso attuale.
    private void trovaPercorso(int stanza, LinkedList<Integer> percorso) {
        // Legge tutti i valori della mappa sulla riga "stanza".
        for (int i : mappa[stanza]) {
            // Saltiamo il valore stanza nella prima posizione e i valori delle stanze che sono già state considerate.
            if (i != 0 && !percorso.contains(i)) {
                // Aggiungiamo il numero alla lista.
                percorso.addLast(i);
                // Verifichiamo se siamo arrivati alla fine.
                if (i == 9999) {
                    System.out.println(percorso);
                    this.trovataSoluzione = true;
                } else
                    // Chiamata ricorsiva.
                    trovaPercorso(i, percorso);
                // Tornando indietro da una chiamata ricorsiva vuol dire che dobbiamo cambiare direzione.
                percorso.removeLast();
            }
        }
    }

    public static void main(String... args) throws IOException {

        // Creiamo un oggetto labirinto e uno scanner per leggere da IO.
        Labirinto labirinto = new Labirinto();
        Scanner scanner = new Scanner(System.in);

        // Se l'apertura del file va a buon fine, facciamo partire la risoluzione.
        if (labirinto.caricaFile(new File(scanner.nextLine()))) {
            int temp = scanner.nextInt();
            if (!labirinto.risolvi(temp))
                System.out.println("nessuna soluzione");
        } else
            System.out.println("file non valido");
    }
}
