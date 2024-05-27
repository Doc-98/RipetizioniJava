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
        for (ArcoIn arcoEntrante : preSet)
            if (!arcoEntrante.abilitato()) return false;
        return true;
    }//abilitata

    // Effettua la transizione, quindi muove i token.
    public void sparo() {

        // Rimuove i token necessari per effettuaer la transizione dai posti di partenza.
        for (ArcoIn arcoEntrante : preSet) {
            Posto postoTemp = arcoEntrante.getPosto();
            postoTemp.setMarcatura(postoTemp.getMarcatura() - arcoEntrante.getPeso());
        }

        // Aggiunge i token appropriati nei posti di arrivo.
        for (ArcoOut arcoUscente : postSet) {
            Posto postoTemp = arcoUscente.getPosto();
            postoTemp.setMarcatura(postoTemp.getMarcatura() + arcoUscente.getPeso());
        }
    }//sparo

    // TO STRING
    // Compone una stringa che descrive la transizione elencando il preset, il peso degli archi in entrata, il postset e il peso degli archi in uscita.
    public String tostring() {

        String s = super.toString() + " preset =";

        for (ArcoIn arcoEntrante : preSet)
            s = s + arcoEntrante.getPosto().getNome() + " peso #" + arcoEntrante.getPeso() + " ";

        s = s + " postset =";

        for (ArcoOut arcoUscente : postSet)
            s = s + arcoUscente.getPosto().getNome() + " peso #" + arcoUscente.getPeso() + " ";
        s = s + "\n";

        return s;
    }
}
