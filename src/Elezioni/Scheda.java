package Elezioni;

import java.util.Iterator;
import java.util.LinkedList;

public class Scheda implements Iterable<String> {

    // ATTRIBUTI
    // Lista di stringhe (ordinata) che rappresenta l'elenco delle preferenze.
    private LinkedList<String> preferenze = new LinkedList<String>();

    // PUBLIC
    // Aggiungo un candidato, passato per argomento, alla lista delle preferenze.
    // (ricordiamo che il metodo ".add" delle liste concatenate aggiunge in coda)
    public void add(String cand) {
        preferenze.add(cand);
    }//add

    // Rimuoviamo un candidato, passato per argomento, alla lista delle preferenze.
    public void remove(String cand) {
        preferenze.remove(cand);
    }//remove

    // EQUALS
    public boolean equals(Object x) {
        if (!(x instanceof Scheda)) return false;
        if (x == this) return true;
        Scheda s = (Scheda) x;
        return preferenze.equals(s.preferenze);
    }//equals

    // HASH CODE
    public int hashCode() {
        return preferenze.hashCode();
    }//hashCode

    // TO STRING
    public String toString() {
        return preferenze.toString();
    }//toString

    // Ritorna un iteratore contenente le stringe presenti nella lista delle preferenze.
    public Iterator<String> iterator() {
        return preferenze.iterator();
    }//iterator
}//Scheda
