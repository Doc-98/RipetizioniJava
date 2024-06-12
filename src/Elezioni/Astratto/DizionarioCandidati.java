package Elezioni.Astratto;

public interface DizionarioCandidati extends Iterable<String> {

    // ADD
    void add(String cand);

    boolean eliminato(String cand);

    // REMOVE
    void elimina(String cand);

    int getNumeroCandidati();

    // CONTAINS
    boolean contieneCandidato(String cand);
}
