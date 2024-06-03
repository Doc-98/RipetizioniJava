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

        Test test = new Test();
        test.makeTrue();
        // CODICE...

        System.out.println("trovato = " + test.convertTrovato());
    }
}
