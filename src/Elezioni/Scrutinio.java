package Elezioni;

import Elezioni.Astratto.Conteggio;
import Elezioni.Astratto.DizionarioCandidati;
import Elezioni.Astratto.Elezione;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class Scrutinio {

    // Legge un file dove sono salvati i nomi dei candidati, uno per riga.
    static void caricaCandidati(File f, DizionarioCandidati dc) throws IOException {

        // Creiamo un reader e una stringa dove salvare quello che leggiamo.
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String cand;

        // Scorriamo il file
        for ( ; ; ) {
            // Leggiamo una linea e verifichiamo la condizione di uscita.
            cand = reader.readLine();
            if (cand == null) break;

            // Rendiamo la stringa maiuscola e aggiungiamo il candidato.
            cand = cand.toUpperCase();
            dc.add(cand);
        }
        // Chiudiamo il reader.
        reader.close();

        // Stampiamo l'elenco candidati.
        System.out.println("Elenco Candidati");
        System.out.println(dc);
    }//caricaCandidati

    // Legge un file dove sono salvate le schede, ogni linea rappresenta una scheda.
    static void caricaElezione(File f,
                               Elezione elez,
                               DizionarioCandidati dc,
                               Situazione sit) throws IOException {

        // Creiamo un reader, una stringa dove salvare quello che leggiamo e un tokenizer per separare le informazioni.
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String linea;
        StringTokenizer tokenizer;

        // Scorriamo il file.
        for ( ; ; ) {
            // Leggiamo una linea e verifichiamo la condizione di uscita.
            linea = reader.readLine();
            if (linea == null) break;

            // Se la linea è vuota, la scheda è bianca.
            if (linea.isEmpty()) {
                sit.setSchedeBianche(sit.getSchedeBianche() + 1); // ? (N.B.: invece che questo metodo brutto, potevamo creare un metodo che aumentasse le schede bianche di 1)
                // Passiamo al prossimo giro del ciclo, poichè non abbiamo nulla da leggere.
                continue;
            }

            // Inizializziamo il tokenizer, creiamo una scheda e dichiariamo un booleano e un intero di controllo.
            tokenizer = new StringTokenizer(linea);
            Scheda scheda = new Scheda();
            boolean schedaValida = true;
            int pref = 0;

            // Finchè ci sono ancora token...
            while (tokenizer.hasMoreTokens()) {
                // Portiamo in maiuscolo e salviamo in una stringa, poi aggiorniamo il contatore.
                String cursoreCand = tokenizer.nextToken().toUpperCase();
                pref++;

                // Se il candidato che abbiamo appena letto non esiste, la scheda è nulla, quindi non valida.
                if (!dc.contieneCandidato(cursoreCand)) {

                    sit.setSchedeNulle(sit.getSchedeNulle() + 1);
                    schedaValida = false;

                    // Usciamo dal while, poichè non ha più senso leggere questa linea.
                    break;

                } else scheda.add(cursoreCand); // Se il candidato invece esiste, lo aggiungiamo alla scheda.
            }//while

            // ! N.B.: Modo terribile di computare la cosa. Segue metodo corretto.
            // Se la scheda è valida ma il numero delle preferenze è diverso dal numero dei candidati, la scheda è nulla.
            if (schedaValida && scheda.size() != dc.getNumeroCandidati()) {
                sit.setSchedeNulle(sit.getSchedeNulle() + 1);
                schedaValida = false;
            }
            // Se la scheda è valida viene aggiunta all'oggetto elezione passato per argomento e viene incrementato il contatore nella situazione.
            if (schedaValida) {
                sit.setSchedeValide(sit.getSchedeValide() + 1);
                elez.addScheda(scheda);
            }
            // ! ---------------------------------------------------------------------------
            // ! ---------------------------------------------------------------------------
            
// *           Formulazione corretta:
// *           if(schedaValida && scheda.size() == dc.getNumeroCandidati()){
// *               sit.setSchedeValide(sit.getSchedeValide() + 1);
// *               elez.addScheda(scheda);
// *           } else {
// *               sit.setSchedeNulle(sit.getSchedeNulle() + 1);
// *           }
        
        }
        
        // Chiudiamo il reader e stampiamo i numeri della situazione schede e le schede stesse
        reader.close();
        System.out.println("Situazione schede:");
        System.out.println(sit);
        System.out.println("Schede da scrutinare");
        System.out.println(elez);
    }

    public static void main(String[] args) throws IOException {
        
        // Inizializziamo i file dai quali prendere i candidati e le schede.
        File f1 = new File("c:\\poo-file\\candidati.txt");
        File f2 = new File("c:\\poo-file\\schede.txt");
        // Gestiamo le eccezioni.
        if (!f1.exists() || !f2.exists()) {
            throw new RuntimeException("File elezione inesistenti");
        }
        
        // Creiamo i nostri oggetti.
        DizionarioCandidati dc = new DizionarioCandidatiMap();
        Situazione sit = new Situazione();
        Elezione elez = new ElezioneLista(dc);

        // Carichiamo i dati contenuti nei file nelle strutture dati.
        caricaCandidati(f1, dc);
        caricaElezione(f2, elez, dc, sit);
        
        // Creiamo un ciclo.
        for ( ; ; ) {
            
            // Contiamo i voti e salviamo il vincitore in una stringa.
            Conteggio c = elez.conta();
            String cand = c.vincitore(sit.maggioranzaAssoluta());
            
            // Se la stringa non è nulla, abbiamo un vincitore, lo stampiamo e usciamo dal ciclo. (finisce anche il main)
            if (cand != null) {
                System.out.println("Il candidato " + cand +
                        " è vincitore con voti: " + c.voti(cand));
                break;
            }
            // Se tutti i candidati rimasti hanno ricevuto lo stesso numero di voti, dichiariamo la parità e usciamo dal ciclo. (e dal main)
            if (c.tuttiMinoritari()) {
                System.out.println("Parita'");
                break;
            }
            
            // Se siamo qui vuol dire che nessun candidato ha raggiunto la maggioranza assoluta, dunque eliminiamo il candidato (o i candidati) con meno voti.
            List<String> minoritari = c.minoritari();
            for (String cm : minoritari) dc.elimina(cm);
        }//for
    }//main
}