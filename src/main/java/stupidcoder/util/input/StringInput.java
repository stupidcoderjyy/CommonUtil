package stupidcoder.util.input;

import java.nio.charset.StandardCharsets;

public class StringInput implements IInput {
    private final byte[] data;
    private int forward;
    private int lexemeStart;

    public StringInput(String str) {
        data = str.getBytes(StandardCharsets.UTF_8);
        forward = 0;
        lexemeStart = 0;
    }

    @Override
    public boolean isOpen() {
        return data != null;
    }

    @Override
    public void markLexemeStart() {
        lexemeStart = forward;
    }

    @Override
    public boolean available() {
        checkOpen();
        return hasNext();
    }

    @Override
    public int read() {
        checkAvailable();
        return data[forward++];
    }

    @Override
    public void skip(int count) {
        forward += count;
        checkAvailable();
    }

    @Override
    public boolean hasNext() {
        return forward < data.length;
    }

    @Override
    public String lexeme() {
        String str = new String(data, lexemeStart, forward - lexemeStart, StandardCharsets.UTF_8);
        lexemeStart = forward;
        return str;
    }

    @Override
    public byte[] bytesLexeme() {
        byte[] res = new byte[forward - lexemeStart];
        System.arraycopy(data, lexemeStart, res, 0, res.length);
        return res;
    }

    @Override
    public int retract() {
        forward = Math.max(0, forward - 1);
        lexemeStart = Math.min(forward, lexemeStart);
        return data[forward];
    }
}
