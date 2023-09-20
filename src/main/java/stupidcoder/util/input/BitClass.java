package stupidcoder.util.input;

public class BitClass {
    private final boolean[] data = new boolean[128];

    public static BitClass of(int ... chs) {
        BitClass clazz = new BitClass();
        for (int ch : chs) {
            clazz.add(ch);
        }
        return clazz;
    }

    BitClass() {
    }

    void add(int b) {
        b &= 0xFF;
        if (b > 127) {
            return;
        }
        data[b] = true;
    }

    boolean accept(int b) {
        b &= 0xFF;
        if (b > 127) {
            return false;
        }
        return data[b];
    }
}
