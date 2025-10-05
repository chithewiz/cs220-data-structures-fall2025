// ==============================================================================
// IMPORTS

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
// ==============================================================================


public class ListGenerator {

    private final AmhList<Integer> list;
    private final Random rng;
    private final PrintStream output;

    public ListGenerator(AmhList<Integer> list, long seed, PrintStream out) {
        this.list = list;
        this.rng = new Random(seed);
        this.output = out;
        // Header
        output.printf("%6s %9s %9s\n", "op", "index", "value");
    }

    private int getRandomIndexForAdd() {
        // valid add indices: 0..size
        int n = list.size();
        return (n == 0) ? 0 : rng.nextInt(n + 1);
    }

    private int getRandomIndexForAccess() {
        // valid indices: 0..size-1 (if empty, return 0 and let caller skip)
        int n = list.size();
        return (n <= 1) ? 0 : rng.nextInt(n);
    }

    private int getRandomValue() {
        return rng.nextInt(100); // keep small for readability
    }

    private void emit(String op, int index, Integer value) {
        output.printf("%6s %9d %9s\n", op, index, (value == null ? "-" : value.toString()));
    }

    public void doAdd() {
        int index = getRandomIndexForAdd();
        int value = getRandomValue();
        try {
            list.add(index, value);
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            // Should not happen with our generator; emit for visibility.
        }
        emit("add", index, value);
    }

    public void doSet() {
        if (list.size() == 0) return;
        int index = getRandomIndexForAccess();
        int value = getRandomValue();
        try {
            list.set(index, value);
        } catch (IndexOutOfBoundsException e) {}
        emit("set", index, value);
    }

    public void doGet() {
        if (list.size() == 0) return;
        int index = getRandomIndexForAccess();
        Integer got = null;
        try {
            got = list.get(index);
        } catch (IndexOutOfBoundsException e) {}
        emit("get", index, got);
    }

    public void doRemove() {
        if (list.size() == 0) return;
        int index = getRandomIndexForAccess();
        Integer old = null;
        try {
            old = list.remove(index);
        } catch (IndexOutOfBoundsException e) {}
        emit("remove", index, old);
    }
// ==========================================================================
    // MAIN METHOD
    // ==========================================================================

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java ListGenerator <ListType> <Seed> <OutputFile>");
            System.out.println("Example: java ListGenerator AmhArrayList 42 output.txt");
            return;
        }

        String type = args[0];
        long seed = Long.parseLong(args[1]);
        String outputPath = args[2];

        AmhList<Integer> list;
        if (type.equals("AmhArrayList")) {
            list = new AmhArrayList<Integer>();
        } else if (type.equals("AmhLinkedList")) {
            list = new AmhLinkedList<Integer>();
        } else {
            System.out.println("Unknown list type. Use AmhArrayList or AmhLinkedList.");
            return;
        }

        try (PrintStream out = new PrintStream(new File(outputPath))) {
            ListGenerator gen = new ListGenerator(list, seed, out);
            for (int i = 0; i < 50; i++) { // generate 50 random operations
                switch (gen.rng.nextInt(4)) {
                    case 0 -> gen.doAdd();
                    case 1 -> gen.doSet();
                    case 2 -> gen.doGet();
                    case 3 -> gen.doRemove();
                }
            }
            out.println("Done generating operations. Final size = " + list.size());
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file: " + outputPath);
        }
    }
}

