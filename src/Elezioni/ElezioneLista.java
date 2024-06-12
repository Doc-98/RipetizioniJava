package Elezioni;

import Elezioni.Astratto.Conteggio;
import Elezioni.Astratto.DizionarioCandidati;
import Elezioni.Astratto.ElezioneAstratta;

import java.util.Iterator;
import java.util.LinkedList;

public class ElezioneLista extends ElezioneAstratta {

    // ATTRIBUTI
    // Lista di schede che contengono le preferenze di ogni elettore.
    private LinkedList<Scheda> urna = new LinkedList<Scheda>();
    private DizionarioCandidati dc;

    // CONSTRUCTOR
    public ElezioneLista(DizionarioCandidati dc) {
        this.dc = dc;
    }

    // PUBLIC
    // Aggiunge una scheda, passata per argomento, all'urna.
    public void addScheda(Scheda s) {
        urna.addLast(s);
    }//addScheda

    // Rimuove una scheda, passata per argomento, dall'urna.
    public void removeScheda(Scheda s) {
        urna.remove(s);
    }//removeScheda

    // Restituisce il conteggio delle schede dell'urna.
    public Conteggio conta() {
        // Inizializza un oggetto Conteggio
        Conteggio c = new ConteggioMap();
        // Per ogni scheda nell'urna...
        for (Scheda s : this) {
            // Per ogni candidato nella scheda...
            for (String cand : s) {
                // Se il candidato non è già stato eliminato...
                if (!dc.eliminato(cand)) {
                    // Se il conteggio non contiene il candidato, lo aggiungiamo.
                    if (!c.contieneCandidato(cand))
                        c.addCandidato(cand);
                    // Incrementiamo di uno il conteggio dei voti di quel candidato.
                    c.incVoti(cand);
                    // Usciamo dal ciclo, poichè vogliamo prendere solo il primo candidato valido.
                    break;
                }
            }
        }
        return c;
    }//conta

    // Ritorna un iteratore contenente gli elementi dell'urna, ovvero le schede.
    public Iterator<Scheda> iterator() {
        return urna.iterator();
    }//iterator
}