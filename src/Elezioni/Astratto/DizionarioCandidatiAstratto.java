package Elezioni.Astratto;

public abstract class DizionarioCandidatiAstratto implements DizionarioCandidati {

    // Conta gli elementi presenti nell'oggetto e li ritorna come intero.
    public int getNumeroCandidati() {
        int n = 0;
        for (String cand : this) n++;
        return n;
    }//getNumeroCandidati

    // ? N.B.: fa la stessa cosa della funzione omonima in "ConteggioAstratto". Non bene.
    public boolean contieneCandidato(String cand) {
        for (String cnd : this)
            if (cnd.equals(cand)) return true;
        return false;
    }//contieneCandidato

    // TO STRING
    public String toString() {
        StringBuilder sb = new StringBuilder(500);
        for (String cand : this) {
            sb.append(cand);
            if (eliminato(cand)) {
                sb.append(' ');
                sb.append("Eliminato");
            }
            sb.append('\n');
        }
        return sb.toString();
    }//toString
}
