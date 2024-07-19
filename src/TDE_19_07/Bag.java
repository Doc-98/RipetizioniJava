package TDE_19_07;

import java.util.Comparator;
import java.util.Iterator;

public interface Bag<T> extends Iterable<T> {
    
    /*
    Quando vogliamo iniziare ad implementare dei metodi già dall'interfaccia, ricordiamo sempre di porci una domanda fondamentale:
        
        QUESTO METODO DOVRÀ ACCEDERE DIRETTAMENTE A DEGLI ATTRIBUTI PER POTER ESSERE IMPLEMENTATO CORRETTAMENTE?
        
    Se la risposta è "SI", passiamo al prossimo metodo, poichè non avendo attributi a livello di interfaccia, non possiamo GARANTIRE come questi attributi verranno definiti dai livelli inferiori.
    Se la risposta è "NO" dovrebbe voler dire che possiamo implementare quel metodo usando solo gli argomenti che gli vengono passati e le altre funzioni dell'interfaccia, il che è accettabile.
    
    L'unica eccezione a questa regola è quando vogliamo scorrere e leggere gli elementi in maniera parametrica in questo modo:
        for(T elem : this){
            [...]
            [code]
            [...]
        }
    Accedere ai dati in questo modo è concesso, ma bisogna comunque stare bene attenti. (vedi problema con metodo "multiplicity")
    */
    
    default int cardinality(){
        int count = 0;
        for(T elem : this)
            count++;
        return count;
    } // ? OK
    default void clear() {
        Iterator<T> it=this.iterator();
        while( it.hasNext() ) {
            it.next();
            it.remove();
        }
    } // ! Come dice il prof, non puoi modificare una struttura dati mentre usi l'iterator.
    
    default int multiplicity(T x){
        int count = 0;
        for(T elem : this){
            if(elem.equals(x)){
                count++;
            }
        }
        return count;
    } // ! Il prof nella soluzione non implementa questo metodo nell'interfaccia, per un motivo simile a quello di clear.
    // ! Non sappiamo ancora come verrà concretamente implementata la struttura dati, quindi contare la molteplicità degli elementi non può essere garantita da un metodo a questo livello di astrazione.
    // ! N.B.: Ricordiamoci sempre che un metodo implementato ad un dato livello, questo deve funzionare a prescindere da come viene implementato il livello inferiore.
    
    /*  Proviamo a considerare cosa succede nella tua implemenazione della classe concreta "BagImpl".
        Se non facciamo l'OVERRIDE di questo metodo, stiamo scorrendo una mappa strutturata nel modo seguente:
        
        [CHIAVE<T>, VALORE<Integer>]
        
        Essendo una mappa, il ciclo for che hai usato per questo metodo andrà a scorrere le chiavi della mappa, che per definizione sono tutte uniche.
        Quindi questo metodo ritornerà sempre 1.
    */
    
    void add(T x);
    // ! I successivi due metodi andranno sempre a richiamare il metodo "add" per accedere alla struttura dati, quindi possono già essere implementati.
    // ! Questo perchè non saranno mai loro stessi a modificare la struttura dati, ma delegheranno sempre il compito alla "add", per questo non dobbiamo aver paura di rompere nulla.
    // ! Infatti il metodo "add" dovrà SEMPRE essere implementato dai livelli inferiori, perchè, come sappiamo bene, l'Interfaccia è un CONTRATTO DI FIDUCIA.
    void add(T x, int multiplicity);
    void addAll(Bag<T> b);
    
    boolean remove(T x);
    boolean removeAll(T x); // ! Stesso discorso della "add".
    
    
    Bag<T> factory();
    Bag<T> factory(Comparator<T> c);
    
    boolean included(Bag<T> b); // ! Ci chiediamo, possiamo implementare questo metodo a livello di Interfaccia? Si -> Vedi sol prof.
    Bag<T> union(Bag<T> b); // ! Come sopra.
    Bag<T> difference(Bag<T> b); // ! Come sopra.
    Bag<T> intersection(Bag<T> b); // ! Come sopra.
}


