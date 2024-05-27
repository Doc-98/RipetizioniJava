package PetriNet;

public class Posto extends Entita {

    // Attibuti
    private int marcatura;

    // Costruttore pricipale
    public Posto(String nome, int marcatura) {
        super(nome);
        if (marcatura < 0) throw new IllegalArgumentException();
        this.marcatura = marcatura;
    }

    // Costruttore secondario (marcatura default)
    public Posto(String nome) {
        this(nome, 0);
    }

    // Costruttore di copia
    public Posto(Posto p) {
        this(p.getNome(), p.getMarcatura());
    }

    // GETTER
    public int getMarcatura() {
        return marcatura;
    }

    // SETTER
    public void setMarcatura(int marcatura) {
        if (marcatura < 0) throw new IllegalArgumentException();
        this.marcatura = marcatura;
    }

    // TO STRING
    public String toString() {
        return super.toString() + "#" + this.marcatura;
    }
}
