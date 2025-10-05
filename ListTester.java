
import java.io.PrintStream;

public class ListTester {

    private static void runScript(String name, AmhList<Integer> list, PrintStream out) {
        out.println("===== " + name + " =====");
        ListGenerator gen = new ListGenerator(list, /*seed*/ 42L, out);

        // Edge cases first
        gen.doAdd();     // add at index 0 when empty
        gen.doGet();     // get index 0
        gen.doSet();     // set index 0
        gen.doAdd();     // possibly append
        gen.doAdd();     // more growth
        gen.doRemove();  // remove somewhere valid
        gen.doGet();     // sanity

        // A few more ops
        for (int i = 0; i < 10; i++) {
            gen.doAdd();
            gen.doSet();
            gen.doRemove();
            gen.doGet();
        }

        out.println("final size = " + list.size());
        out.println();
    }

    public static void main (String[] args) {
        PrintStream out = System.out;

        runScript("AmhArrayList", new AmhArrayList<Integer>(), out);
        runScript("AmhLinkedList", new AmhLinkedList<Integer>(), out);
    }
    //in the command line type: java LIsttester AmhArrayList output.txt
}