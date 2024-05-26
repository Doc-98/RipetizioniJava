package PetriNet;

public abstract class Arco {

    // Attributi
    private Posto p;
    private int peso;

    // Costruttore principale
    public Arco(Posto p, int peso) {
        if (peso < 0)
            throw new IllegalArgumentException();
        this.p = p;
        this.peso = peso;
    }

    // Costruttore secondario (peso default)
    public Arco(Posto p) {
        this(p, 1);
    }

    // GETTERS
    Posto getPosto() {
        return p;
    }

    public int getPeso() {
        return peso;
    }

    // TO STRING
    public String toString() {
        return "Arco peso:" + peso;
    }
}