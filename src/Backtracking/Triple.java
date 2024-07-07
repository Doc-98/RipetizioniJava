package Backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Triple extends Backtracking<Integer,Integer>{

    private int[] arrayDiNumeri, triplettaDiControllo;
    private int valoreSomma, numSol = 0;
    private List<Set<Integer>> soluzioni = new ArrayList<>();

    //Costruttore
    public Triple(int[] array, int somma) {

        // Controlliamo che l'array ricevuto sia di più di 3 elementi.
        if(array.length <= 3)
            throw new IllegalArgumentException("" + array.length + "<=3");

        // Controlliamo che non ci siano duplicati, sfruttando le proprietà dei set<>
        Set<Integer> s = new HashSet<>();
        for( int w: array )
            s.add(w);

        // Se il set nel quale abbiamo copiato tutti gli elementi dell'array non è della stessa lunghezza, vuol dire che sono stati trovati dei duplicati.
        if(s.size() != array.length)
            throw new IllegalArgumentException("Esistono duplicati.");

        // Una volta controllate le precondizioni, copiamo l'array passato per argomento nella variabile del nostro oggetto.
        this.arrayDiNumeri = java.util.Arrays.copyOf(array, array.length);
        // Copiamo anche il numero somma nella nostra variabile valoreSomma dell'oggetto
        this.valoreSomma = somma;
        // Infine inizializziamo l'array tripletteDiControllo a un array vuoto di dimensione 3.
        this.triplettaDiControllo = new int[3];
    }

    // Costruiamo una lista di numero di elementi pari alla lunghezza dell'array di controllo e ritorniamo il riferimento al primo elemento.
    protected List<Integer> costruisciListaPuntiScelta(){
        List<Integer> listaPuntiScelta = new ArrayList<>();
        for(int i = 0; i < triplettaDiControllo.length; ++i ) listaPuntiScelta.add(i);
        return listaPuntiScelta;
    }

    // Costruiamo anche una collezione nella quale inseriamo tutti gli elementi dell'arrayDiNumeri.
    protected Collection<Integer> creaCollezioneElementi(Integer elem){
        List<Integer> s = new ArrayList<>();
        for (int j : arrayDiNumeri) s.add(j);
        return s;
    }

    // I due metodi precedenti sono serviti a conformare ai tipi di dato usati dai metodi della classe padre, gli elementi della classe figlio.

    // Definiamo il metodo che determina se un elemento "elem" passato per argomento può essere considerato valido.
    // Ovviamente il metodo ritorna un booleano true se l'elemento è assegnabile, altrimenti ritorna false.
    protected boolean assegnabile(Integer cursoreListaScelti, Integer elem) {
        // Dichiariamo un intero che ci servirà per contare la somma a cui siamo arrivati.
        int somma = 0;

        // Scorriamo l'array "triplettaDiControllo" fino all'ultimo spazio libero.
        for(int i = 0; i < cursoreListaScelti; ++i ) {
            // Poiché stiamo considerando tutte le combinazioni possibili in maniera ricorsiva, succederà che a volte ci troviamo a provare ad aggiungere un numero che è già stato considerato.
            // Per evitare questa ripetizione, ci basta controllare sempre che il valore che stiamo valutando ("elem") non sia già stato inserito.
            // In caso sia così, entriamo nell'if e facciamo la return di false, terminando il metodo.
            // Questa cosa è fattibile poiché sappiamo per costruzione che l'"arrayDiNumeri" è senza duplicati, quindi se troviamo due volte lo stesso numero, è per forza una ripetizione da scartare.
            if( this.triplettaDiControllo[i] == elem)
                return false;

            // A questo punto posso sommare il valore che stiamo guardando nell'array "triplettaDiControllo" nella variabile "somma".
            somma = somma + this.triplettaDiControllo[i];
        } // Una volta finito questo ciclo for, nella variabile "somma" avremo per l'appunto la somma di tutti i valori già inseriti nell'array "triplettaDiControllo".

        // Ora controlliamo se sommare "elem" ci farà superare il nostro obiettivo, ovvero quello salvato in "valoreSomma".

        // Se, contato anche "elem", non abbiamo ancora finito di compilare l'array "triplettaDiControllo" && aggiungere "elem" non ci fa superare "valoreSomma", possiamo assegnare "elem", quindi ritorniamo true.
        if( cursoreListaScelti < this.triplettaDiControllo.length - 1 && somma + elem < this.valoreSomma) return true;

        // Se sono alla fine dell'array triplettaDiControllo una volta aggiunto "elem", ci basta controllare se "elem" + la somma degli elementi già presenti nell'array è uguale al "valoreSomma"
        // In questo caso vuol dire che abbiamo una soluzione e che quindi "elem" può essere aggiunto alla lista degli scelti, quindi ritorniamo true.
        if( cursoreListaScelti == this.triplettaDiControllo.length - 1 && somma + elem == this.valoreSomma) return true;
        // Se nessuna delle due precedenti viene soddisfatta, vuol dire che "elem" non può contribuire a creare una soluzione, dunque non è assegnabile e dunque ritorniamo false.
        return false;
    }

    // Metodo per aggiungere all'array "triplettaDiControllo" uno degli elementi che è stato determinato come "assegnabile" dal metodo precedente.
    protected void assegna(Integer cursoreListaScelti, Integer elemDaAssegnare) {
        triplettaDiControllo[cursoreListaScelti]= elemDaAssegnare;
    }

    // In questo caso non abbiamo bisogno di deassegnare nulla, perché finiamo per sovrascrivere questi dati durante gli altri metodi.
    protected void deassegna(Integer elemControllo, Integer elem) {}//deassegna

    // Implementiamo il metodo per stampare le nostre soluzioni.
    protected void scriviSoluzione( Integer elemControllo) {
        // Aggiorniamo il contatore con il quale teniamo traccia del numero della soluzione a cui ci troviamo.
        numSol++;
        // Stampiamo l'inizio della nostra formattazione.
        System.out.print(numSol + ": [");
        // Creiamo un loop per stampare tutti gli elementi dell'array che forma la soluzione con una virgola che li separa.
        for(int i = 0; i <= elemControllo; ++i ) {
            System.out.print(triplettaDiControllo[i]);
            if( i < elemControllo) System.out.print(",");
        }
        System.out.println("]"); // Stampiamo la chiusura della parentesi.
    }

    // Metodo per verificare di aver trovato una soluzione valida e non ripetuta.
    @SuppressWarnings("DuplicatedCode")
    protected boolean esisteSoluzione(Integer cursoreListaScelti) {
        // Verifichiamo che il cursore sia sull'ultimo elemento dell'array dove teniamo le possibili soluzioni.
        if( cursoreListaScelti < triplettaDiControllo.length-1 ) return false;

        // Creiamo un nuovo set per verificare di non aver trovato una soluzione duplicata.
        Set<Integer> sol = new HashSet<>();

        // Ci mettiamo dentro tutti gli elementi dell'array di controllo.
        // Chiaramente essendo un set, qualsiasi tentativo di aggiungere un duplicato verrà ignorato.
        for( int v: triplettaDiControllo) sol.add(v);

        // Creiamo uno stream di set di interi, lo chiamiamo "controlStream" e facciamo lo stream del set "soluzioni".
        // Soluzioni è una lista di set di interi, dove noi andiamo a memorizzare tutte le triplette che abbiamo trovato.
        Stream<Set<Integer>> controlStream = this.soluzioni.stream();

        // A questo punto filtriamo lo stream di "soluzioni" e contiamo se troviamo match con "sol".
        // Questo perchè al momento abbiamo una soluzione valida dal punto di vista della somma dei suoi numeri.
        // Ma dobbiamo anche capire se l'abbiamo già considerata, confrontandola con tutte le soluzioni precedentemente considerate.
        int count = (int)controlStream.filter(s -> s.equals(sol)).count();

        // Qui la soluzione viene memorizzata a prescindere che ci sia già un doppione o meno.
        // Si potrebbe ottimizzare, ma effettivamente l'efficacia del codice rimane intatta.
        this.soluzioni.add(sol); //memorizza nuova soluzione

        // A questo punto se "c" è ancora uguale a 0, vuol dire che la soluzione che stiamo considerando non ha duplicati fra quelle già trovate.
        // Quindi ritorniamo c == 0 poiché se lo è abbiamo effettivamente trovato una nuova soluzione, altrimenti dobbiamo ritornare false.
        return count == 0;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, -1};
        int x = 7;
        new Triple(a, x).risolvi();
    }

}