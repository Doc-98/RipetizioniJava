package Backtracking;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class Backtracking<P,S> {

    // Metodo da definire nella classe figlia per determinare se un elemento "elem" soddisfa i criteri necessari per essere scelto.
    protected abstract boolean assegnabile(P cursoreListaScelti, S elem);

    // Metodo da definire nella classe figlia per andare effettivamente ad assegnare alla lista degli elementi scelti, un elemento che è stato determinato assegnabile.
    protected abstract void assegna(P cursoreListaScelti, S elemDaAssegnare);

    // Metodo da definire nella classe figlia per rimuovere l'assegnamento di due elementi.
    protected abstract void deassegna(P cursore, S elem);

    // Metodo da definire nella classe figlia per definire la stampa dei risultati.
    protected abstract void scriviSoluzione(P elemControllo);

    // Lista dei punti che sono stati scelti.
    private List<P> listaPuntiScelta;

    // Metodo da definire nella classe figlia per costruire la lista degli elementi da "scegliere"
    // La definizione viene lasciata alla classe figlia per garantire la massima versatilità del metodo.
    protected abstract List<P> costruisciListaPuntiScelta();

    // Metodo da definire nella classe figlia per costruire una collezione di oggetti dai quali scegliere le soluzioni.
    // Anche qui lasciamo la definizione alla classe figlia per versatilità.
    protected abstract Collection<S> creaCollezioneElementi(P elem);

    // Metodo che ritorna sempre il primo elemento della lista dei punti scelti.
    private P primoPuntoDiScelta() {
        return listaPuntiScelta.get(0);
    }

    // Metodo che prende come argomento un elemento della lista degli elementi scelti e ritorna il riferimento al prossimo elemento.
    private P prossimoPuntoDiScelta(P elem) {
        if(esisteSoluzione(elem))
            throw new NoSuchElementException();
        int i = listaPuntiScelta.indexOf(elem);
        return listaPuntiScelta.get(i + 1);
    }

    // Metodo che ritorna true se l'elemento passato come argomento è l'ultimo elemento della lista, altrimenti ritorna false.
    private boolean ultimoPuntoDiScelta(P elem) {
        int i = listaPuntiScelta.indexOf(elem);
        return i == listaPuntiScelta.size() - 1;
    }

    // Metodo che viene ridefinito con override nella classe figlia.
    // Generalmente dovrebbe andare a ritornare true quando è stata soddisfatta una condizione di soluzione.
    protected boolean esisteSoluzione(P cursoreListaScelti) {
        return false;
    }

    // Metodo che viene ridefinito con ovveride nella classe figlia nel caso in cui ci si voglia fermare prima di aver controllato tutte le possibili soluzioni.
    protected boolean ultimaSoluzione(P elem) {
        return false; //cerca tutte le possibili soluzioni
    }//ultimaSoluzione

    protected void tentativo(P cursoreListaScelti) {

        // Dichiariamo una collezione di elementi con l'apposita funzione. Questi saranno tutti gli elementi da analizzare per trovare le soluzioni.
        Collection<S> collezioneElementi = creaCollezioneElementi(cursoreListaScelti);

        // Creiamo un loop per tutta la collezione di elementi che abbiamo appena creato.
        for( S elem: collezioneElementi) {

            // Se il metodo "ultimaSoluzione" non viene ridefinito, non entreremo mai in questo if e controlleremo tutte le combinazioni possibili.
            // Altrimenti nel momento in cui entriamo in questo if, la funzione termina.
            if( ultimaSoluzione(cursoreListaScelti) )
                break;

            // Verifichiamo se l'elemento della collezione attualmente preso in considerazione ("elem") è assegnabile alla lista dei punti scelti
            if( assegnabile(cursoreListaScelti, elem) ) {

                // Se entriamo in questo if vuol dire che l'elemento "elem" va aggiunto alla lista dei punti scelti, quindi lo facciamo chiamando la funzione "assegna".
                assegna(cursoreListaScelti, elem);

                // Ogni volta che aggiungiamo un nuovo elemento, verifichiamo se abbiamo raggiunto la condizione di una soluzione.
                if( esisteSoluzione(cursoreListaScelti) )
                    // In caso positivo, scriviamo la soluzione in questione.
                    scriviSoluzione(cursoreListaScelti);

                    // Se non abbiamo trovato una soluzione, ci troviamo in uno di due casi:
                    //   1 - Non abbiamo ancora finito di aggiungere elementi alla lista dei punti scelti.
                    //   2 - Abbiamo completato una possibile combinazione della lista dei punti scelti, ma questa non soddisfa le condizioni necessarie per essere una soluzione.
                    // Per distinguere fra i due casi, chiamiamo il metodo "ultimoPuntoDiScelta()" che ci ritorna un booleano.
                    // Questo sarà true se il nostro cursore si trova sull'ultima posizione della lista (e quindi se abbiamo inserito anche l'ultimo elemento), o false altrimenti.
                else if( !ultimoPuntoDiScelta(cursoreListaScelti) )
                    // Se non siamo arrivati all'ultimo elemento della lista punti scelti, facciamo una chiamata ricorsiva di questa funzione.
                    // Questa volta come argomento, passiamo il prossimo elemento della lista.
                    tentativo(prossimoPuntoDiScelta(cursoreListaScelti));

                // Una volta finito il ciclo, (se è stato fatto override) deassegnamo il cursore e l'elemento "elem".
                deassegna(cursoreListaScelti, elem);
            }
        }
    }//tentativo

    protected void risolvi() {
        listaPuntiScelta = costruisciListaPuntiScelta();
        tentativo( primoPuntoDiScelta() );
    }//risolvi

}//Backtracking.Backtracking