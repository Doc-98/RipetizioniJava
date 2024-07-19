package TDE_19_07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class BagImpl<T> extends AbstractBag<T>{
    
    // * I metodi "factory" mi sembrano tutti corretti.
    @Override
    public BagImpl<T> factory(){
        return new BagImpl<>();
    }
    public BagImpl(){
        super.elements = new HashMap<>();
    }
    public BagImpl(Comparator<T> c){
        super.elements =  new TreeMap<>(c);
    }
    
    @Override
    public Bag<T> factory(Comparator<T> c){
        return new BagImpl<>(c);
    }
    
    // * Errorino di distrazione ma a parte quello corretto.
    public static void costruisciBag(String nomeFile, Bag<String> bag){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(nomeFile));
            String line = br.readLine();
            line = line.toUpperCase(); // ! Questo andava messo nel while.
            while (line != null) {
                bag.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public T maxMultiplicyty(){
        int max = -1;
        T maxParola = null;
        for(T elem : this){
            int mult = this.multiplicity(elem);
            if(mult > max){
                max = mult;
                maxParola = elem;
            }
        }
        return maxParola;
    }
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Nome file: ");
        String nomeFile = scanner.nextLine();
        scanner.close();
        
        Comparator<String> c = (s1, s2) -> {
            int lens1 = s1.length();
            int lens2 = s2.length();
            int diff = lens1 - lens2;
            if(diff != 0)
                return diff;
            return s1.compareTo(s2);
        };
        BagImpl<String> bag = new BagImpl<>(c);
        costruisciBag(nomeFile, bag);
        System.out.println(bag);
        
        String parolaMax = bag.maxMultiplicyty();
        System.out.println("Parola che ricorre più frequentemente: "+ parolaMax);
    }
    
}

