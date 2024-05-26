package PetriNet;

import java.util.List;

public class Transizione extends Entita {

    // Attributi
    private List<ArcoIn> preSet;
    private List<ArcoOut> postSet;

    // Cosruttore
    public Transizione(String nome, List<ArcoIn> preSet, List<ArcoOut> postSet) {
        super(nome);
        this.preSet = preSet;
        this.postSet = postSet;
    }

    // Ritorna true se tutti i suoi archi entranti sono abilitati, altrimenti false.
    public boolean abilitata() {
        for (ArcoIn ai : preSet)
            if (!ai.abilitato()) return false;
        return true;
    }//abilitata

    // Effettua la transizione, quindi muove i token.
    public void sparo() {

        // Rimuove i token necessari per effettuaer la transizione dai posti di partenza.
        for (ArcoIn ai : preSet) {
            Posto p = ai.getPosto();
            p.setMarcatura(p.getMarcatura() - ai.getPeso());
        }

        // Aggiunge i token appropriati nei posti di arrivo.
        for (ArcoOut au : postSet) {
            Posto p = au.getPosto();
            p.setMarcatura(p.getMarcatura() + au.getPeso());
        }
    }//sparo

    // TO STRING
    // Compone una stringa che descrive la transizione elencando il preset, il peso degli archi in entrata, il postset e il peso degli archi in uscita.
    public String tostring() {

        String s = super.toString() + " preset =";

        for (ArcoIn ai : preSet)
            s = s + ai.getPosto().getNome() + " peso #" + ai.getPeso() + " ";

        s = s + " postset =";

        for (ArcoOut au : postSet)
            s = s + au.getPosto().getNome() + " peso #" + au.getPeso() + " ";
        s = s + "\n";

        return s;
    }
}
