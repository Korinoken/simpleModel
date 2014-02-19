package modelLogic;

public class NormalDestribution extends UniformDestribution {
    double mju, sigma;

    public NormalDestribution(long seed, double mju, double sigma) {
        super(seed, 0, 1); //0 to 1 uniform destribution
        this.mju = mju;
        this.sigma = sigma;
    }

    public double nextNumber() {
        double res = 0;
        int setLength = 100;
        for (int i = 0; i < setLength; i++) {
            res += super.nextNumber();
        }
        res = (res - (setLength / 2)) / Math.sqrt(setLength / 12);
        return res * sigma + mju;
    }
}
