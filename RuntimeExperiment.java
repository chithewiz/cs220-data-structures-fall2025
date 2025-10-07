import java.util.Random;

public class RuntimeExperiment {

    private Random rand;

    public void go(double p) {
        rand = new Random(42);
        int n = 2097152; // 2^21
        for(int j = 0; j < 5; j++) {
            runTest(n, p);
        }
    }

    public void runTest(int n, double p) {
        SkipList<Integer> list = new SkipList<Integer>(p);
        for(int i = 0; i <= n; i++) {
            list.add(rand.nextInt(n*10));
            if((i & (i - 1)) == 0){
                list.find(rand.nextInt(n*10));
                System.out.print(list.getOps() + " ");
            }
        }
        System.out.println();
    }


    public static void main(String[] args) {
        double p = 0.5;
        if(args.length > 0) {
            try{
                p = Double.parseDouble(args[0]);
            }
            catch(NumberFormatException e) {
                System.out.println("No argument given, using p = 0.5.");
            }
        }

        RuntimeExperiment r = new RuntimeExperiment();
        r.go(p);
    }
}
