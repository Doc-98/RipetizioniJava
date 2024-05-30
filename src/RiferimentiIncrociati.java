import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class RiferimentiIncrociati {

    static TreeMap<String, TreeSet<Integer>> indice =
            new TreeMap<>(
                    (String s1, String s2) -> {
                        if (s1.length() < s2.length()) return -1;
                        if (s1.length() > s2.length()) return 1;
                        return s1.compareTo(s2);
                    }
            );

    static boolean verificaFile(File f) throws IOException {
        String regex = "\\w+:\\d+";
        BufferedReader br = new BufferedReader(new FileReader(f));
        String linea = null;
        try {
            for (; ; ) {
                linea = br.readLine();
                if (linea == null) break;
                if (!linea.matches(regex)) return false;
            }
        } finally {
            br.close();
        }
        return true;
    }//verificaFile

    static void caricaFile(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String linea = null;
        StringTokenizer st = null;
        for (; ; ) {
            linea = br.readLine();
            if (linea == null) break;
            st = new StringTokenizer(linea, ":");
            String p = st.nextToken();
            int l = Integer.parseInt(st.nextToken());
            if (!indice.containsKey(p)) {
                indice.put(p, new TreeSet<>());
            }
            indice.get(p).add(l);
        }
        br.close();
    }

    static void visualizzaIndice() {
        for (String p : indice.keySet()) {
            System.out.println(p);
            System.out.print("\t");
            for (int n : indice.get(p))
                System.out.print(n + " ");
            System.out.println();
        }
    }

    static void scriviIndice(File fo) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(fo));
        for (String p : indice.keySet()) {
            pw.println(p);
            pw.print("\t");
            for (int n : indice.get(p))
                pw.print(n + " ");
            pw.println();
        }
        pw.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        File fi = null, fo = null;
        String nomeFi = null, nomeFo = null;
        System.out.print("Nome file di ingresso: ");
        nomeFi = sc.nextLine();
        fi = new File(nomeFi);
        if (!fi.exists())
            throw new RuntimeException("File di ingresso inesistente!");
        System.out.print("Nome file di uscita: ");
        nomeFo = sc.nextLine();
        fo = new File(nomeFo);
        if (!verificaFile(fi))
            throw new RuntimeException("File di ingresso non corretto");
        caricaFile(fi);
        visualizzaIndice();
        scriviIndice(fo);
    }//main

}//RiferimentiIncrociati