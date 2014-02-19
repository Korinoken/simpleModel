package modelLogic;

public final class ErlangDistribution extends EksponentialDestribution {
    int l;

    public ErlangDistribution(long seed, double lambda, int l) {
        super(seed, lambda);
        this.l = l;
    }

    public double nextNumber() {
        double res = 0;
        for (int i = 0; i < l; i++) {
            res += super.nextNumber();
        }
        return res;
    }
}
