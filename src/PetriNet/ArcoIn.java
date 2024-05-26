package PetriNet;

public class ArcoIn extends Arco {

    // Costruttore principale
    public ArcoIn(Posto p, int peso) {
        super(p, peso);
    }

    // Costruttore secondario (peso default)
    public ArcoIn(Posto p) {
        this(p, 1);
    }

    // TO STRING
    public String toString() {
        return super.toString() + " di ingresso";
    }

    // Ritorna un true se il posto è abilitato, falso altrimenti.
    public boolean abilitato() {
        return getPosto().getMarcatura() >= getPeso();
    }
}