package Elezioni.Astratto;

import java.util.List;

public interface Conteggio extends Iterable<String> {

    int voti(String cand);

    void incVoti(String cand);

    // CONTAINS
    boolean contieneCandidato(String cand);

    // ADD
    void addCandidato(String cand);

    List<String> minoritari();

    boolean tuttiMinoritari();

    String vincitore(int maggAss);
}