package stupidcoder.util.generate.sources;

import stupidcoder.util.generate.Source;

import java.util.function.Supplier;

public abstract class SourceField<T> extends Source {
    protected int size;
    protected byte[] data;
    private Supplier<T> supplier;
    private int pos;

    public SourceField(String id, Supplier<T> supplier) {
        super(id);
        this.supplier = supplier;
    }

    protected abstract int getSize(T val);

    protected abstract void writeBytes(T val);

    @Override
    public void lock() {
        initData();
    }

    @Override
    protected void recall() {
        pos = 0;
        initData();
    }

    private void initData() {
        T v = supplier.get();
        size = getSize(v);
        data = new byte[size];
        writeBytes(v);
    }

    @Override
    public int read(byte[] arr, int offset, int len) {
        used = true;
        if (pos == size) {
            pos = 0;
        }
        int actualLen = Math.min(len, size - pos);
        if (actualLen == 0) {
            return 0;
        }
        System.arraycopy(data, pos, arr, offset, actualLen);
        pos += actualLen;
        return size;
    }

    @Override
    public void close() {
    }

    @Override
    public void destroy() {
    }

    protected final void writeInt(int v, int start) {
        data[start] = (byte) (v >> 24);
        data[start + 1] = (byte) (v >> 16);
        data[start + 2] = (byte) (v >> 8);
        data[start + 3] = (byte) v;
    }
}
