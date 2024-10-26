package stupidcoder.util.generate;

import stupidcoder.util.input.IByteReader;

public abstract class Source implements IByteReader {
    protected final String name;
    protected boolean used = false;

    public Source(String name) {
        this.name = name;
    }

    public abstract void lock();

    protected void recall() {
        throw new UnsupportedOperationException("cannot recall disposable sources:" + this);
    }

    public void destroy() {
        close();
    }

    @Override
    public String toString() {
        return name + "(" + getClass().getSimpleName() + ")";
    }

    public static Source EMPTY = new Source("") {
        @Override
        public void lock() {
        }

        @Override
        public int read(byte[] bytes, int i, int i1) {
            return 0;
        }

        @Override
        public void close() {
        }
    };
}
