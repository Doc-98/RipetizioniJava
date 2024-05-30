public class Sudoku {

    // Attributi
    private int[][] board = new int[9][9]; //tutti 0 per default
    private boolean[][] blocked = new boolean[9][9];//celle bloccate-tutti false iniz
    private int numSol = 0;

    // Costruttore con stato iniziale.
    public Sudoku(int[][] board) {

        for (int i = 0; i < board.length; ++i) {
            // Recuperiamo la tripletta.
            int riga = board[i][0], colonna = board[i][1], valore = board[i][2];

            // Controlliamo che i valori siano validi
            if (riga < 0 || riga >= 9 || colonna < 0 || colonna >= 9 || valore < 1 || valore > 9)
                throw new IllegalArgumentException();

            // Salviamoci la posizione nella matrice delle celle bloccate.
            blocked[riga][colonna] = true;

            // Controlliamo che comunque l'assegnamento sia valido prima di confermarlo.
            if (assegnabile(riga, colonna, valore)) {
                this.board[riga][colonna] = valore;
            } else
                throw new IllegalArgumentException();
        }
    }

    // Metodo che inizializza la risoluzione ricorsiva su un oggetto Sudoku.
    public void risolvi() {
        colloca(0, 0);
    }

    // Metodo ricorsivo che effettua la risoluzione effettiva.
    // N.B: Nel nostro esercizio "colloca" viene chiamata solo da "risolvi" o da se stessa.
    // In questo modo inizia sempre dal punto (0, 0). Tuttavia teniamo a mente che il metodo funziona su qualsiasi sotto griglia che gli viene passata.
    private void colloca(int riga, int colonna) {
        if (this.blocked[riga][colonna]) {
            if (riga == this.board.length - 1 && colonna == this.board[riga].length - 1)
                scriviSoluzione();
            else {
                if (colonna == this.board[riga].length - 1) colloca(riga + 1, 0);
                else colloca(riga, colonna + 1);
            }
        } else {
            for (int s = 1; s <= 9; ++s) {
                if (assegnabile(riga, colonna, s)) {
                    assegna(riga, colonna, s);
                    if (riga == this.board.length - 1 && colonna == this.board[riga].length - 1)
                        scriviSoluzione();
                    else {
                        if (colonna == this.board[riga].length - 1) colloca(riga + 1, 0);
                        else colloca(riga, colonna + 1);
                    }
                    deassegna(riga, colonna);
                }
            }
        }
    }//colloca

    // Prende due coordinate e un valore, ritorna true se allo stato attuale della griglia il valore può essere assegnato in quel punto, falso altrimenti.
    private boolean assegnabile(int i, int j, int valore) {

        // Sfruttiamo l'arrotondamento per difetto di java per ottenere solo valori multipli di 3.
        // In questo modo ci allineiamo sempre al primo quadretto in alto a sinistra del quadrante da cui partiamo.
        int riga = (i / 3) * 3, colonna = (j / 3) * 3;

        // Verifica unicità nel quadrante di appartenenza
        for (int cursoreRiga = riga; cursoreRiga <= riga + 2; ++cursoreRiga)
            for (int cursoreColonna = colonna; cursoreColonna <= colonna + 2; ++cursoreColonna)
                if (this.board[cursoreRiga][cursoreColonna] == valore) return false;

        // Verifica unicità stessa riga
        for (int cursoreColonna = 0; cursoreColonna < 9; ++cursoreColonna)
            if (this.board[i][cursoreColonna] == valore) return false;

        // Verifica unicità stessa colonna
        for (int cursoreRiga = 0; cursoreRiga < 9; ++cursoreRiga)
            if (this.board[cursoreRiga][j] == valore) return false;
        return true;
    }//assegnabile

    // Prende due coordinate e un valore, inserisce nella griglia il valore alla data coordinata.
    private void assegna(int i, int j, int s) {
        this.board[i][j] = s;
    }//assegna

    // Riceve due coordinate, azzera il valore della griglia nella data coordinata.
    private void deassegna(int i, int j) {
        //cella certamente non bloccata
        this.board[i][j] = 0;
    }//deassegna

    // Aggiorna il contatore delle soluzioni e stampa la griglia.
    private void scriviSoluzione() {
        this.numSol++;
        System.out.println("SolNum#" + this.numSol + ": ");
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j)
                System.out.print(this.board[i][j] + "  ");
            System.out.println();
        }
    }//scriviSoluzione

    // TO STRING
    public String toString() {
        StringBuilder strBuilder = new StringBuilder(500);

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j)
                strBuilder.append(this.board[i][j] + "  ");
            strBuilder.append('\n');
            }

        return strBuilder.toString();
    }//toString

    // MAIN
    public static void main(String[] args) { //DEMO
        int[][] a = {
                {0, 0, 5}, {0, 1, 3}, {0, 4, 7},
                {1, 0, 6}, {1, 3, 1}, {1, 4, 9}, {1, 5, 5},
                {2, 1, 9}, {2, 2, 8}, {2, 7, 6},
                {3, 0, 8}, {3, 4, 6}, {3, 8, 3},
                {4, 0, 4}, {4, 3, 8}, {4, 5, 3}, {4, 8, 1},
                {5, 0, 7}, {5, 4, 2}, {5, 8, 6},
                {6, 1, 6}, {6, 6, 2}, {6, 7, 8},
                {7, 3, 4}, {7, 4, 1}, {7, 5, 9}, {7, 8, 5},
                {8, 7, 7}, {8, 8, 9}
        };

        int[][] b = {
                {0, 5, 9},
                {1, 1, 3}, {1, 2, 4}, {1, 4, 1}, {1, 6, 5}, {1, 7, 6},
                {2, 0, 8}, {2, 5, 2}, {2, 8, 7},
                {3, 0, 6}, {3, 2, 2},
                {4, 1, 7}, {4, 3, 3}, {4, 4, 4}, {4, 5, 1}, {4, 7, 2},
                {5, 6, 1}, {5, 8, 8},
                {6, 0, 1}, {6, 3, 8}, {6, 8, 4},
                {7, 1, 6}, {7, 2, 9}, {7, 4, 7}, {7, 6, 8}, {7, 7, 3},
                {8, 3, 5}
        };
        Sudoku su = new Sudoku(b);
        System.out.println(su);
        System.out.println();
        System.out.println("INIZIO SOLUZIONI");
        su.risolvi();
        System.out.println("FINE SOLUZIONI");

    }//main
}//Sudoku