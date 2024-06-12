package Elezioni.Astratto;

import java.util.List;

public abstract class ConteggioAstratto implements Conteggio {

    public abstract int voti(String cand);

    public abstract void incVoti(String cand);

    // Scorre le stringhe contenute nell'oggetto, ritorna true se la stringa argomento è presente, false altrimenti.
    public boolean contieneCandidato(String cand) {
        for (String cnd : this)
            if (cnd.equals(cand)) return true;
        return false;
    }//contieneCandidato

    public abstract void addCandidato(String cand);

    public abstract List<String> minoritari();

    public abstract boolean tuttiMinoritari();

    public abstract String vincitore(int maggAss);

    // TO STRING
    public String toString() {
        StringBuilder sb = new StringBuilder(500);
        for (String cand : this) {
            sb.append(cand);
            sb.append(' ');
            sb.append(voti(cand));
            sb.append('\n');
        }
        return sb.toString();
    }//toString
}
