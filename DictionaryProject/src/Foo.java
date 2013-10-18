public class Foo {
    private static final String INSERT = "12 10 8 14 6 9";

    public static void main(String[] args) {
        SplayTree<Integer> s = new SplayTree<Integer>();
        for (String i : INSERT.split(" ")) {
            System.out.println();
            System.out.println("Inserting " + i);
            s.insert(Integer.parseInt(i));
            System.out.println(s);
        }
    }
}