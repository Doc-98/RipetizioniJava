package Elezioni;

import Elezioni.Astratto.ConteggioAstratto;

import java.util.*;

public class ConteggioMap extends ConteggioAstratto {

    // ATTRIBUTI
    // Mappa dove vengono salvati i candidati come chiavi e il numero dei voti ricevuti come valori.
    private Map<String, Integer> voti = new HashMap<>();

    // Ritorna il numero di voti salvato nella struttura dati tramite la chiave passata per argomento.
    public int voti(String cand) {
        if (voti.containsKey(cand))
            return voti.get(cand);
        throw new RuntimeException("Candidato illegale"); // Lanciata  se la chiave non esiste.
    }//voti


    // PUBLIC
    // Aumenta di uno il valore salvato nella struttura dati in corrispondenza della chiave passata per argomento.
    public void incVoti(String cand) {
        if (voti.containsKey(cand)) {
            int v = voti.get(cand);
            v++;
            voti.put(cand, v);
// *           N.B.: per fare la stessa cosa in una riga e senza dichiarare una variabile:
// *           voti.replace(cand, voti.get(cand) + 1);
            return;
        }
        throw new RuntimeException("Candidato illegale"); // Lanciata  se la chiave non esiste.
    }//incVoti

    // Ritorna true se il candidato è contenuto nella struttura dati, false altrimenti.
    public boolean contieneCandidato(String cand) {
        return voti.containsKey(cand);
    }//contieneCandidato

    // Aggiunge un valore alla struttura dati, chiave passata per argomento e valore iniziallizzato a 0.
    public void addCandidato(String cand) {
        voti.put(cand, 0);
    }//addCandidato

    // Ritorna una lista di stringhe con i nomi di tutti i candidati che hanno ottenuto il minor numero di voti.
    // (potenzialmente anche una lista con un solo elemento)
    public List<String> minoritari() {
        int min = Integer.MAX_VALUE;
        for (String cand : this) {
            int v = voti(cand);
            if (v < min) min = v;
        }
        
        LinkedList<String> minori = new LinkedList<String>();
        
        for (String cand : this) {
            int v = voti(cand);
            if (v == min) minori.add(cand);
        }
        return minori;
    }//daEliminare

    // Ritorna true se tutti i candidati hanno ottenuto lo stesso numero di voti, false altrimenti.
    public boolean tuttiMinoritari() {
        List<String> min = minoritari();
        for (String cand : voti.keySet()) {
            if (!min.contains(cand)) return false;
        }
        return true;
    }//tuttiMinoritari

    // Ritorna il nome del candidato che ha ottenuto un numero di voti pari o maggiore all'intero passato per argomento, false altrimenti.
    // ? (N.B.: brutto modo di gestire la cosa)
    public String vincitore(int maggAss) {
        for (String cand : this) {
            if (voti(cand) >= maggAss) return cand;
        }
        return null;
    }//vincitore

    // Ritorna un iteratore contenente il keyset della mappa usata come struttura dati dell'oggetto.
    public Iterator<String> iterator() {
        return voti.keySet().iterator();
    }//iterator
}