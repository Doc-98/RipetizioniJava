package Elezioni.Astratto;

import Elezioni.Scheda;

public interface Elezione extends Iterable<Scheda> {

    // ADD
    void addScheda(Scheda s);

    // REMOVE
    void removeScheda(Scheda s);

    Conteggio conta();
}
