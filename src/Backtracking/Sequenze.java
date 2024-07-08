package Backtracking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Sequenze extends Backtracking<Integer, Integer> {
    
    private final int n, controlSum;
    private File output;
    private int[] controlArray;
    private Set<SortedSet<Integer>> soluzioni = new HashSet<>();
    
    public Sequenze(int n, File f) {
        
        if(n < 3) throw new IllegalArgumentException();
        
        this.n = n;
        this.controlSum = this.n *(this.n * this.n + 1)/2;
        this.output = f;
        this.controlArray = new int[this.n];
        
    }
    
    @Override
    protected boolean assegnabile(Integer cursoreListaScelti, Integer elem) {
        
        int somma = 0;
        
        for (int i = 0; i < cursoreListaScelti; i++) {
            if (this.controlArray[i] == elem)
                return false;
            
            somma += this.controlArray[i];
        }
        
        // Se non abbiamo ancora finito di riempire il controlArray && l'attuale somma dei valori, più l'elemento che vogliamo aggiungere non sfora la controlSum
        if(cursoreListaScelti < this.controlArray.length - 1 && somma + elem < this.controlSum) return true;
        
        if(cursoreListaScelti == this.controlArray.length - 1 && somma + elem == this.controlSum) return true;
        
        return false;
    }
    
    @Override
    protected void assegna(Integer cursoreListaScelti, Integer elemDaAssegnare) {
        this.controlArray[cursoreListaScelti] = elemDaAssegnare;
    }
    
    @Override
    protected void deassegna(Integer elemControllo, Integer elem) {
    
    }
    
    @SuppressWarnings("DuplicatedCode")
    protected boolean esisteSoluzione(Integer cursoreListaScelti) {
        
        if( cursoreListaScelti < controlArray.length-1 ) return false;

        SortedSet<Integer> sol = new TreeSet<>();

        for( int value : controlArray) sol.add(value);
        
        if(soluzioni.contains(sol)) return false;
        
        soluzioni.add(sol);
        
        return true;
    }
    
    @Override
    protected void scriviSoluzione(Integer elemControllo) {
        
        StringBuilder builder = new StringBuilder();
        
        for(int i = 0; i < elemControllo + 1; i++) {
            builder.append(this.controlArray[i] + " ");
        }
        
        System.out.println(builder);
    }
    
    protected void scriviSolSuFile() throws IOException {
        
        PrintWriter outputWriter = new PrintWriter(new FileWriter(this.output));
        
        for(Set<Integer> set : this.soluzioni) {
            for (int i : set){
                outputWriter.print(i + " ");
            }
            outputWriter.println();
        }
        
        outputWriter.close();
    }
    
    @Override
    protected List<Integer> costruisciListaPuntiScelta() {
        
        List<Integer> listaPuntiScelta = new ArrayList<>();
        
        for(int i = 0; i < controlArray.length; i++) {
            listaPuntiScelta.add(i);
        }
        
        return listaPuntiScelta;
    }
    
    @Override
    protected Collection<Integer> creaCollezioneElementi(Integer elem) {
        
        List<Integer> elementi = new ArrayList<>();
        
        for(int i = 1; i <= n * n; i++) {
            elementi.add(i);
        }
        
        return elementi;
    }
    
    public static void main(String[] args) throws IOException {
        
        File f = new File("soluzioni.txt");
        
        Sequenze seq = new Sequenze(4, f);
        
        seq.risolvi();
        seq.scriviSolSuFile();
        
    }
}
