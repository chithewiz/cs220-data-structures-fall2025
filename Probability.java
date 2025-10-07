import java.util.Random;

public class Probability {
    private final Random rng;
    public Probability() { this.rng = new Random(); }
    public Probability(long seed) { this.rng = new Random(seed); }

    // Bernoulli(p): returns 0 or 1
    public int getBernoulli(double p) {
        if (p < 0 || p > 1) throw new IllegalArgumentException("p in [0,1]");
        return rng.nextDouble() < p ? 1 : 0;
    }

    // Binomial(n,p): sum of n i.i.d. Bernoulli(p)
    public int getBinomial(int n, double p) {
        if (n < 0) throw new IllegalArgumentException("n >= 0");
        int s = 0;
        for (int i = 0; i < n; i++) s += getBernoulli(p);
        return s;
    }

    // Geometric(p): # of flips until the first heads (1-indexed per spec)
    public int getGeometric(double p) {
        if (p <= 0 || p >= 1) {
            if (p == 1.0) return 1;         // always heads on first flip
            throw new IllegalArgumentException("0 < p <= 1");
        }
        int k = 1;
        while (rng.nextDouble() >= p) k++;
        return k;
    }

    // quick driver to emit frequencies/means as CSV-friendly text
    public static void main(String[] args) {
        Probability pr = new Probability(42L);
        int trials = 1000;

        double[] ps = {0.1, 0.5, 0.9};

        // Bernoulli hist & mean
        for (double p : ps) {
            int ones = 0;
            for (int i = 0; i < trials; i++) ones += pr.getBernoulli(p);
            int zeros = trials - ones;
            double mean = ones / (double) trials;
            System.out.printf("Bernoulli p=%.2f, 0:%d (%.3f), 1:%d (%.3f), mean=%.4f%n",
                    p, zeros, zeros/(double)trials, ones, ones/(double)trials, mean);
        }

        // Binomial(100,p) mean check
        for (double p : ps) {
            long sum = 0;
            for (int i = 0; i < trials; i++) sum += pr.getBinomial(100, p);
            System.out.printf("Binomial(100,%.2f) mean=%.4f (theory=%.2f)%n",
                    p, sum/(double)trials, 100*p);
        }

        // Geometric(p) mean check (theory 1/p)
        for (double p : ps) {
            long sum = 0;
            for (int i = 0; i < trials; i++) sum += pr.getGeometric(p);
            System.out.printf("Geometric(%.2f) mean=%.4f (theory=%.4f)%n",
                    p, sum/(double)trials, 1.0/p);
        }
    }
}
//to run: javac Probability.java && java Probability