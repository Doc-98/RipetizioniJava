package PetriNet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class PN {

    // Attributi
    private Map<String, Posto> mappaPosti;
    private LinkedList<Transizione> abilitate = new LinkedList<>();
    private LinkedList<Transizione> disabilitate = new LinkedList<>();


    // Costruttore
    public PN(Map<String, Posto> mappaPosti, LinkedList<Transizione> listaTransizioni) {
        this.mappaPosti = mappaPosti;
        for (Transizione t : listaTransizioni) {
            if (t.abilitata()) abilitate.add(t);
            else disabilitate.add(t);
        }
    }


    // Fa procedere il sistema di un passo.
    public void singleStep() {

        // Se mai dovessimo avere la lista delle abilitate vuota, vuol dire che siamo in un deadlock!
        if (abilitate.isEmpty()) {
            System.out.println("Deadlock!");
            return;
        }

        // Randomizziamo l'ordine della lista delle transizioni abilitate, creiamo un iteratore e un cursore.
        // Salviamo il primo valore dell'iteratore nel cursore e lo rimuoviamo dalla lista.
        // Lo aggiungiamo alla lista delle transizioni disabilitate per ora.
        Collections.shuffle(abilitate);
        Iterator<Transizione> iterTranAbil = abilitate.iterator();
        Transizione cursoreTran = iterTranAbil.next();
        iterTranAbil.remove();
        disabilitate.add(cursoreTran); //pessimismo

        // Effettuiamo un passo.
        cursoreTran.sparo();

        // Scorriamo tutto il cursore delle transizioni abilitate per verificare se lo sono ancora dopo lo sparo.
        while (iterTranAbil.hasNext()) {

            Transizione q = iterTranAbil.next();

            if (!q.abilitata()) {
                disabilitate.add(q);
                iterTranAbil.remove();
            }
        }

        // Facciamo la stessa cosa con la lista delle transizioni disabilitate.
        Iterator<Transizione> iterTranDisab = disabilitate.iterator();
        while (iterTranDisab.hasNext()) {
            cursoreTran = iterTranDisab.next();
            if (cursoreTran.abilitata()) {
                iterTranDisab.remove();
                abilitate.add(cursoreTran);
            }
        }

        // Stampiamo lo stato della rete dopo aver sviluppato il passo.
        System.out.println("Marcatura dopo lo sparo di " + cursoreTran.getNome());
        for (String s : mappaPosti.keySet()) {
            System.out.print(mappaPosti.get(s) + " ");
            System.out.println();
        }//singleStep
    }

    // Il metodo per fare più passi assieme non è altro che un ciclo for fino al numero di volte che vengono passate per argomento.
    // Controlliamo solo che il numero che passiamo non sia negativo e che la lista di transizioni abilitate non sia mai vuota.
    public void multipleSteps(int stepNumber) {
        if (stepNumber < 0) throw new IllegalArgumentException();
        for (int i = 0; i < stepNumber; ++i) {
            if (abilitate.isEmpty()) {
                System.out.println("Deadlock!");
                return;
            }
            singleStep();
        }
    }//multipleSteps
}//PN
