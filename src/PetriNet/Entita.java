package PetriNet;

public abstract class Entita {

    // Attributi
    private final String nome;

    // Construttore
    public Entita(String nome) {
        this.nome = nome;
    }

    // GETTER
    public String getNome() {
        return nome;
    }

    // TO STRING
    public String toString() {
        return nome;
    }

    // EQUALS
    public boolean equals(Object obj) {
        if ( !(obj instanceof Entita) ) return false;
        if (obj == this) return true;
        Entita ent = (Entita) obj;
        return this.nome.equals(ent.nome);
    }

    /// HASHCODE
    public int hashCode() {
        return nome.hashCode();
    }
}
