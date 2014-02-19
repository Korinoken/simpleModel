package modelLogic;

import java.nio.ByteBuffer;

import java.security.SecureRandom;

import java.util.Random;

import modelInterfaces.Randomizer;

public class UniformDestribution implements Randomizer {
    protected long seed;
    protected Random randomizer;
    private int lBound, hBound;
    private byte[] byteSeed;

    /**
     * @param seed
     * @param lBound minumal random number
     * @param hBound maximum random number
     */
    public UniformDestribution(long seed, int lBound, int hBound) {
        super();
        this.seed = seed;
        this.lBound = lBound;
        this.hBound = hBound;
        byteSeed = ByteBuffer.allocate(8).putLong(seed).array();
        randomizer = new SecureRandom(byteSeed);
    }

    @Override
    public double nextNumber() {
        return lBound + (hBound - lBound) * randomizer.nextDouble();
    }

    @Override
    public long[] makeSet(int size) {
        return new long[0];
    }

    /**
     * @return Returns seed of the generator
     */
    @Override
    public long getSeed() {
        return this.seed;
    }
}
