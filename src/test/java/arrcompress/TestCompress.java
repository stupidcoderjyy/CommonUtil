package arrcompress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.arrcompressor.ArrayCompressor;
import stupidcoder.util.arrcompressor.ICompressedArraySetter;

public class TestCompress implements ICompressedArraySetter {
    private String[] data;
    private int[] start;
    private int[] offsets;

    @Test
    public void test() {
        ArrayCompressor compressor = new ArrayCompressor(this);
        for (int i = 3 ; i <= 6 ; i ++) {
            compressor.set(0, i, "0-" + i);
        }
        compressor.set(0, 50, "0-10");
        compressor.set(2,5,"2-5");
        compressor.set(3,1,"3-1");
        compressor.set(4,6,"4-6");
        compressor.finish();
        for (int i = 3 ; i <= 6 ; i ++) {
            Assertions.assertEquals("0-" + i, getNext(0, i));
        }
        Assertions.assertEquals("2-5", getNext(2, 5));
        Assertions.assertEquals("3-1", getNext(3, 1));
        Assertions.assertEquals("4-6", getNext(4, 6));
        Assertions.assertNull(getNext(4, 7));
        Assertions.assertNull(getNext(4, 5));
        Assertions.assertNull(getNext(12, 10));
        Assertions.assertNull(getNext(1, -15));
    }

    @Override
    public void setData(int i, String val) {
        data[i] = val;
    }

    @Override
    public void setStart(int i, int pos) {
        start[i] = pos;
    }

    @Override
    public void setOffset(int i, int offset) {
        offsets[i] = offset;
    }

    @Override
    public void setSize(int dataSize, int startSize, int offsets) {
        this.data = new String[dataSize];
        this.start = new int[startSize];
        this.offsets = new int[offsets];
    }

    private String getNext(int arg1, int arg2) {
        if (arg1 < 0 || arg1 >= start.length || start[arg1] < 0) {
            return null;
        }
        int limit = arg1 == start.length - 1 ? data.length : start[arg1 + 1];
        int o = arg2 - offsets[arg1];
        if (o < 0) {
            return null;
        }
        int pos = start[arg1] + o;
        return pos < limit ? data[pos] : null;
    }
}
