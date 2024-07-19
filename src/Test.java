import TDE_19_07.BagImpl;

public class Test {

    private boolean trovato;

    public Test() {
        trovato = false;
    }

    public int convertTrovato() {

        if(!this.trovato)
            return 1;

        else return 0;

    }

    public void makeTrue() {
        this.trovato = true;
    }

    public void makeFalse() {
        this.trovato = false;
    }

    public void switchTrovato() {
        this.trovato = !this.trovato;
    }

    public static void main(String[] args) {
        
        BagImpl<String> map = new BagImpl<>();
        map.add("A", 1);
        map.add("B", 2);
        map.add("A", 3);
        map.add("D", 3);
        
        System.out.println(map.multiplicity("A"));
        System.out.println(map.multiplicity("B"));
        System.out.println(map.multiplicity("C"));
        System.out.println(map.multiplicity("D"));
    }
}
