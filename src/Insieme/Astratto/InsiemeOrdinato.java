package Insieme.Astratto;

public interface InsiemeOrdinato<T> extends Insieme<T> {

    // Ritorna il primo (minimo) elemento dell’insieme
    T first();
    // Ritorna l’ultimo (massimo) elemento dell’insieme
    T last();

    // Ritorna il sottoinsieme di this contenente gli elementi minori di x
    InsiemeOrdinato<T> headSet(T x);
    // Ritorna il sottoinsieme di this contenente gli elementi maggiori o uguali ad x.
    InsiemeOrdinato<T> tailSet(T x);

}
