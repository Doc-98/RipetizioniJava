package PetriNet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Creiamo i 4 posti
        Posto posto1 = new Posto("p1", 2),
                posto2 = new Posto("p2"),
                posto3 = new Posto("p3"),
                posto4 = new Posto("p4");

        // Inseriamo i 4 posti in una mappa.
        Map<String, Posto> mappaPosti = new HashMap<>();
        mappaPosti.put("p1", posto1);
        mappaPosti.put("p2", posto2);
        mappaPosti.put("p3", posto3);
        mappaPosti.put("p4", posto4);

        // Creiamo le 5 transizioni.
        // nome: t1; preSet: p1; postSet: p2
        Transizione transizione1 = new Transizione("t1", Arrays.asList(new ArcoIn(posto1)), Arrays.asList(new ArcoOut(posto2)));
        // nome: t2; preSet: p2; postSet: p3
        Transizione transizione2 = new Transizione("t2", Arrays.asList(new ArcoIn(posto2)), Arrays.asList(new ArcoOut(posto3)));
        // nome: t3; preSet: p3; postSet: p4
        Transizione transizione3 = new Transizione("t3", Arrays.asList(new ArcoIn(posto2)), Arrays.asList(new ArcoOut(posto4)));
        // nome: t4; preSet: p3, p4; postSet: p1
        Transizione transizione4 = new Transizione("t4", Arrays.asList(new ArcoIn(posto3), new ArcoIn(posto4)), Arrays.asList(new ArcoOut(posto1)));
        // nome: t5; preSet: p4; postSet: p1
        Transizione transizione5 = new Transizione("t5", Arrays.asList(new ArcoIn(posto4)), Arrays.asList(new ArcoOut(posto1)));

        // Mettiamo tutte le transizioni in una lista.
        LinkedList<Transizione> listaTransizioni = new LinkedList<>(java.util.Arrays.asList(transizione1, transizione2, transizione3, transizione4, transizione5));

        // Stampiamo lo stato iniziale.
        System.out.println("Marcatura iniziale:");
        for (String s : mappaPosti.keySet())
            System.out.println(mappaPosti.get(s) + " ");
        System.out.println();

        // Inizializiamo il network e facciamolo sviluppare per 5 passi.
        PN petriNetwork = new PN(mappaPosti, listaTransizioni);
        petriNetwork.multipleSteps(5);
    }//main
}//Main
