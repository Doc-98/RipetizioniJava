package Elezioni;

public class Situazione {

    // ATTRIBUTI
    private int schedeNulle, schedeBianche, schedeValide;

    // GETTERS
    public int getSchedeNulle() {
        return schedeNulle;
    }

    public int getSchedeBianche() {
        return schedeBianche;
    }

    public int getSchedeValide() {
        return schedeValide;
    }//getSchedeValide

    // SETTERS
    public void setSchedeNulle(int schedeNulle) {
        this.schedeNulle = schedeNulle;
    }//setSchedeNulle

    public void setSchedeBianche(int schedeBianche) {
        this.schedeBianche = schedeBianche;
    }//setSchedeBianche

    public void setSchedeValide(int schedeValide) {
        this.schedeValide = schedeValide;
    }//setSchedeValide

    // Ritorna un intero che rappresenta la metà più uno delle schede contate.
    public int maggioranzaAssoluta() {
        return (schedeNulle + schedeBianche + schedeValide) / 2 + 1;
    }//maggioranzaAssoluta

    // TO STRING
    public String toString() {
        return "schede nulle: " + schedeNulle + "\n" +
                "schede bianche: " + schedeBianche + "\n" +
                "schede valide: " + schedeValide + "\n" +
                "maggioranza assoluta: " + maggioranzaAssoluta() + "\n";
    }//toString

    /* Metodi che sarebbero stati utili:
    public void addSchedaNulla() {
        schedeNulle++;
    }
    public void addSchdaBianca() {
        schedeBianche++;
    }
    public void addSchedaValida() {
        schedeValide++;
    } */

}
