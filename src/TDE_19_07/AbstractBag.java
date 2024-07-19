package TDE_19_07;

import java.util.Iterator;
import java.util.Map;

public abstract class AbstractBag<T> implements Bag<T>{
    
    protected Map<T, Integer> elements;
    
    @Override
    public void add(T x) {
        elements.put(x, multiplicity(x) + 1);
    }
    
    // ! ATTENZIONE BAD HABIT: Hai già il metodo "add" che accede alla struttura dati. Limitare il numero di metodi che lo fanno, garantisce solidità al codice.
    // ! In questo caso avremmo solo dovuto chiamare il metodo "add" un numero di volte pari all'argomento "multiplicity" passato.
    // ! Che è quello che fa il prof nella soluzione dell'interfaccia.
    @Override
    public void add(T x, int multiplicity) {
        if (multiplicity > 0) {
            elements.put(x, multiplicity(x) + multiplicity);
        }
    }
    
    // * Qui hai fatto la cosa giusta, l'unica pecca è non averlo implementato nell'interfaccia.
    @Override
    public void addAll(Bag<T> b) {
        for (T element : b) {
            add(element, b.multiplicity(element));
        }
    }
    
    // * Giusto, segue versione leggermente più pulita.
    @Override
    public boolean remove(T x) {
        int currentMultiplicity = multiplicity(x);
        if (currentMultiplicity > 0) {
            if (currentMultiplicity == 1)
                elements.remove(x);
            else
                elements.put(x, currentMultiplicity - 1);
            
            return true;
        }
        return false;
    }
    
    
    /*
    public boolean remove(T x) {
        
        if(!elements.containsKey(x))
            return false;
        
        int currentMultiplicity = multiplicity(x);
        
        if (currentMultiplicity == 1) {
            elements.remove(x);
            return true;
        }
        else {
            elements.put(x, currentMultiplicity - 1);
            return true;
        }
    }
     */
    
    // * Giusto, segue versione decisamente più pulita.
    public boolean removeAll(T x){
        int currentMultiplicity = multiplicity(x);
        if (currentMultiplicity > 0) {
            elements.remove(x);
            return true;
        }
        return false;
    }
    
    /*
    // .remove(Object key) ritorna il precedente valore associato a "key" se lo trova, null altrimenti.
    
    public boolean removeAll(T x){
        return elements.remove(x) != null;
    }
     */
    
    // ! Reminder che tutti i seguenti metodi eccetto il toString e l'Iterator sarebbe stato meglio implementarli dall'interfaccia.
    
    // * Giusto, ma mettiamo "this." prima del primo "multiplicity(element)" dell'if statement.
    @Override
    public boolean included(Bag<T> b) {
        for (T element : elements.keySet())
            if (multiplicity(element) > b.multiplicity(element))
                return false;
        return true;
    }
    
    // * Perfetto, preferisco questo a quello del prof per l'utilizzo della funzione Math.max.
    @Override
    public Bag<T> union(Bag<T> b) {
        Bag<T> newBag = factory();
        for (T elem : elements.keySet()) {
            newBag.add(elem, Math.max(multiplicity(elem), b.multiplicity(elem)));
        }
        for (T elem : b) {
            if (!elements.containsKey(elem)) {
                newBag.add(elem, b.multiplicity(elem));
            }
        }
        return newBag;
    }
    
    // * Anche qui preferisco il tuo metodo, molto pulito.
    // * (nella soluzione di questa il prof ha fatto una cosa super arzigogolata)
    @Override
    public Bag<T> difference(Bag<T> b) {
        Bag<T> newBag = factory();
        for (T elem : elements.keySet()) {
            int differenza = Math.max(multiplicity(elem) - b.multiplicity(elem),0);
            if (differenza != 0) {
                newBag.add(elem, differenza);
            }
        }
        return newBag;
    }
    
    // * Corretto (di nuovo soluzione del prof un po' bruttina).
    @Override
    public Bag<T> intersection(Bag<T> b) {
        Bag<T> newBag = factory();
        for (T elem : elements.keySet()) {
            int min_occorrenza = Math.min(multiplicity(elem), b.multiplicity(elem));
            if (min_occorrenza > 0) { // * Se proprio devo cercare il pelo nell'uovo, qui avrei messo != 0, poichè la molteplicità non può essere negativa.
                newBag.add(elem, min_occorrenza);
            }
        }
        return newBag;
    }
    
    // * Piccola imperfezione ma fa il suo dovere.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(T key : elements.keySet()){
            Integer occorrenze = elements.get(key);
            sb.append(key).append("(").append(occorrenze).append("), "); // ! Qui vai ad aggiungere la virgola e lo spazio anche dopo l'ultimo elemento.
        }
        sb.append("]");
        return sb.toString();
    }
    
    // * Perfetto
    @Override
    public Iterator<T> iterator() {
        return elements.keySet().iterator();
    }
}
