package Elezioni;

import Elezioni.Astratto.DizionarioCandidatiAstratto;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DizionarioCandidatiMap extends DizionarioCandidatiAstratto {

    // ATTRIBUTI
    // Mappa dove vengono salvati i candidati come chiavi e un booleano, che ne indica il loro stato nell'elezione, come valore.
    private Map<String, Boolean> elenco = new TreeMap<String, Boolean>();

    // PUBLIC
    // Aggiunge un candidato, passato per argomento, alla struttura dati e ne inizializza il valore a false.
    public void add(String cand) {
        elenco.put(cand, false);
    }//add

    // Ritorna il valore riferito ad un candidato passato per argomento.
    public boolean eliminato(String cand) {
        if (elenco.containsKey(cand)) {
            return elenco.get(cand);
        }
        throw new RuntimeException("Candidato inesistente"); // Lanciata  se la chiave non esiste.
    }//eliminato

    // Prende un candidato, passato per argomento, e ne imposta il valore a true.
    public void elimina(String cand) {
        if (elenco.containsKey(cand)) {
            elenco.put(cand, true);
            return;
        }
        throw new RuntimeException("Candidato inesistente"); // Lanciata  se la chiave non esiste.
    }//elimina

    // Ritorna il numero di candidati presente nella struttura dati.
    public int getNumeroCandidati() {
        return elenco.keySet().size();
    }//getNumeroCandidati

    // Ritorna true se il candidato passato per argomento è presente nella struttura dati, false altrimenti.
    public boolean contieneCandidato(String cand) {
        return elenco.containsKey(cand);
    }//contieneCandidato

    // Ritorna un iteratore contenente il keyset della mappa usata come struttura dati dell'oggetto.
    public Iterator<String> iterator() {
        return elenco.keySet().iterator();
    }//iterator
}