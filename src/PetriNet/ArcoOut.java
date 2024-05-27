package PetriNet;

public class ArcoOut extends Arco {

    // Costruttore principale
    public ArcoOut(Posto p, int peso) {
        super(p, peso);
    }

    // Costruttore secondario (peso default)
    public ArcoOut(Posto p) {
        super(p);
    }

    // TO STRING
    public String toString() {
        return super.toString() + " di uscita verso il posto " + this.getPosto().getNome();
    }
}
