package Insieme;

public class Main {

    public static void main(String[] args) {

        InsiemeArray<Integer> array = new InsiemeArray();

        array.add(1);
        array.add(2);
        array.add(3);
        array.add(5);
        array.add(5);
        array.add(6);
        array.add(7);

        System.out.println(array);
    }
}
