package Backtracking;

import java.util.*;

public class Combinazioni extends Backtracking<Integer, Integer>{
    
    private final int n;
    private int[] controlArray;
    private List<SortedSet<Integer>> soluzioni = new ArrayList<>();
    
    // Costruttore.
    public Combinazioni(int n, int k) {
        this.n = n;
        controlArray = new int[k];
    }
    
    // Definisco l'assegnabilità di un elemento.
    @Override
    protected boolean assegnabile(Integer cursoreListaScelti, Integer elem) {
        for (int i = 0; i < cursoreListaScelti; i++) {
            if (this.controlArray[i] == elem)
                return false;
        }
        return true;
    }
    
    // Salvo un elemento di una possibile soluzione nell'array di controllo.
    @Override
    protected void assegna(Integer cursoreListaScelti, Integer elemDaAssegnare) {
        this.controlArray[cursoreListaScelti] = elemDaAssegnare;
    }
    
    @Override
    protected void deassegna(Integer elemControllo, Integer elem) {
    
    }
    
    // Verifico che non ci siano soluzioni duplicate
    @SuppressWarnings("DuplicatedCode")
    protected boolean esisteSoluzione(Integer cursoreListaScelti) {
        
        if( cursoreListaScelti < controlArray.length-1 ) return false;
        
        SortedSet<Integer> sol = new TreeSet<>();
        
        for( int value : controlArray) sol.add(value);
        
        if(soluzioni.contains(sol)) return false;
        
        soluzioni.add(sol);
        
        return true;
    }
    
    // Scrivo la soluzione che sto guardando.
    @Override
    protected void scriviSoluzione(Integer elemControllo) {
        System.out.println(Arrays.toString(controlArray));
    }
    
    // Creo una lista di numeri che tiene il conto della posizione nella soluzione.
    @Override
    protected List<Integer> costruisciListaPuntiScelta() {
        
        List<Integer> listaPuntiScelta = new ArrayList<Integer>();
        
        for(int i = 0; i < controlArray.length; i++) {
            listaPuntiScelta.add(i);
        }
        
        return listaPuntiScelta;
    }
    
    // Salvo tutti gli elementi da analizzare.
    @Override
    protected Collection<Integer> creaCollezioneElementi(Integer elem) {
        
        List<Integer> elementi = new ArrayList<Integer>();
        
        for(int i = 1; i <= this.n; i++) {
            elementi.add(i);
        }
        
        return elementi;
    }
    
    public static void main(String[] args) {
        new Combinazioni(5, 3).risolvi();
    }
}
