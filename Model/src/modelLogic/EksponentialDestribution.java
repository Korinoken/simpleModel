package modelLogic;

public  class EksponentialDestribution extends UniformDestribution {
    double lambda;

    public EksponentialDestribution(long seed, double lambda) {
        super(seed, 0, 1);
        this.lambda = lambda;
    }

    public double nextNumber() {
        double i = super.nextNumber();
        if (i != 1 && i != 0) {
            return -(1 / lambda) * Math.log(1 - i);
        } else {
            return this.nextNumber();
        }

    }
}
