package stupidcoder.util.input;

import stupidcoder.util.input.readers.ConsoleByteReader;
import stupidcoder.util.input.readers.FileByteReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BufferedInput extends AbstractDebugInput {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private static final int MAX_BUFFER_SIZE = 4096;
    private static final byte INPUT_END = -1;
    private final int bufEndA, bufEndB;
    private final int maxLexemeLen;
    private final IByteReader reader;
    private byte[] buffer;
    private int forward;
    private int lexemeStart;
    private int fillCount;
    private boolean ignoreLexemeLimit = false;

    public BufferedInput(IByteReader reader, int bufSize) {
        if (bufSize <= 0 || bufSize > MAX_BUFFER_SIZE) {
            throw new IllegalArgumentException("invalid size:" + bufSize + ", required:(0, 4096]");
        }
        if (reader == null) {
            throw new NullPointerException("null reader");
        }
        this.buffer = new byte[bufSize * 2];
        this.forward = 0;
        this.lexemeStart = 0;
        this.reader = reader;
        this.bufEndA = bufSize;
        this.bufEndB = bufSize * 2;
        this.maxLexemeLen = bufSize;
        fill(0);
    }

    public BufferedInput(IByteReader reader) {
        this(reader, DEFAULT_BUFFER_SIZE);
    }

    public static BufferedInput fromFile(String filePath, int bufSize) {
        try {
            return new BufferedInput(new FileByteReader(filePath), bufSize);
        } catch (FileNotFoundException e) {
            throw new InputException(null, e);
        }
    }

    public static BufferedInput fromFile(String filePath) {
        return fromFile(filePath, DEFAULT_BUFFER_SIZE);
    }

    public static BufferedInput fromResource(String srcPath, int bufSize) {
        try {
            InputStream stream = BufferedInput.class.getResourceAsStream(srcPath);
            if (stream == null) {
                throw new FileNotFoundException(srcPath);
            }
            return new BufferedInput(new FileByteReader(stream), bufSize);
        } catch (FileNotFoundException e) {
            throw new InputException(null, e);
        }
    }

    public static BufferedInput fromResource(String srcPath) {
        return fromResource(srcPath, DEFAULT_BUFFER_SIZE);
    }

    public static BufferedInput fromConsole(int bufSize) {
        return new BufferedInput(new ConsoleByteReader(), bufSize);
    }

    public static BufferedInput fromConsole() {
        return new BufferedInput(new ConsoleByteReader());
    }

    public void ignoreLexemeLengthLimit() {
        this.ignoreLexemeLimit = true;
    }

    @Override
    public boolean isOpen() {
        return buffer != null;
    }

    @Override
    public boolean available() {
        if (!hasNext()) {
            return false;
        }
        if (ignoreLexemeLimit) {
            return true;
        }
        if (lexemeStart <= forward) {
            return forward - lexemeStart < maxLexemeLen;
        }
        return forward + bufEndB - lexemeStart < maxLexemeLen;
    }

    @Override
    public int read() {
        checkAvailable();
        byte result = buffer[forward];
        forward++;
        if (forward == bufEndB) {
            forward = 0;
            if (fillCount % 2 == 0) {
                fill(0);
            }
        } else if (forward == bufEndA) {
            if (fillCount % 2 == 1) {
                fill(bufEndA);
            }
        }
        return result;
    }

    @Override
    public void skip(int count) {
        for (int i = 0 ; i < count ; i ++) {
            read();
        }
    }

    private void fill(int begin) {
        fillCount++;
        int size = reader.read(buffer, begin, bufEndA);
        if (size < bufEndA) {
            buffer[begin + size] = INPUT_END;
        }
    }

    @Override
    public int readUnsigned() {
        return read() & 0xFF;
    }

    @Override
    public boolean hasNext() {
        return buffer[forward] != INPUT_END;
    }

    @Override
    public String lexeme() {
        checkOpen();
        final int start = lexemeStart;
        lexemeStart = forward;
        if (start < forward) {
            return new String(buffer, start, forward - start, StandardCharsets.UTF_8);
        } else if (start > forward){
            int lenB = bufEndB - start;
            int lenA = forward;
            byte[] temp = new byte[lenB + lenA];
            System.arraycopy(buffer, start, temp, 0, lenB);
            System.arraycopy(buffer, 0, temp, lenB, lenA);
            return new String(temp, StandardCharsets.UTF_8);
        }
        return "";
    }

    @Override
    public void markLexemeStart() {
        lexemeStart = forward;
    }

    @Override
    public void retract(int count) {
        checkOpen();
        int tf = forward - count;
        if (tf > 0) {
            forward = tf;
            if (lexemeStart < bufEndA && lexemeStart >= forward) {
                lexemeStart = forward - 1;
            }
        } else {
            if (fillCount == 1) {
                throw new InputException(this, "can not retract: buffer B not loaded");
            }
            if (fillCount % 2 == 0) { //已经加载了右侧的B
                throw new InputException(this, "can not retract: exceed retract limit");
            }
            if (tf < 0) {
                //从A退到B
                tf = bufEndB + tf;
                if (tf <= bufEndA) {
                    throw new InputException(this, "can not retract: exceed retract limit");
                }
                lexemeStart = lexemeStart > bufEndA ? Math.min(tf, lexemeStart) : tf;
            } else if (lexemeStart < bufEndA) {
                //res为0
                lexemeStart = 0;
            }
            forward = tf;
        }
    }

    @Override
    public void close() {
        buffer = null;
        try {
            reader.close();
        } catch (IOException e) {
            throw new InputException(this, e);
        }
    }

    @Override
    protected String type() {
        return "BufferedInput(bufSize = " + bufEndA + ")";
    }

    @Override
    protected void printData() {
        System.err.println("    [A]");
        printBuffer(buffer, 0, bufEndA);
        System.err.println("    [B]");
        printBuffer(buffer, bufEndA, bufEndB);
    }

    @Override
    protected void printPos() {
        printField("forward", forward);
        printField("lexemeStart", lexemeStart);
        printField("fillCount", fillCount);
    }
}
